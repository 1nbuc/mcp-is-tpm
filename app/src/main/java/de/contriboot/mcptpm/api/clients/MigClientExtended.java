package de.contriboot.mcptpm.api.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;

import com.figaf.integration.tpm.client.mig.MessageImplementationGuidelinesClient;

import de.contriboot.mcptpm.api.entities.AgreementEntitiy;
import de.contriboot.mcptpm.api.entities.mapper.MessageImplementationGuidelineMapper;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import de.contriboot.mcptpm.api.entities.mig.MIGProposalRequest;
import de.contriboot.mcptpm.api.entities.mig.MigEntityUtils;
import de.contriboot.mcptpm.utils.JsonUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static java.lang.String.format;

import de.contriboot.mcptpm.api.entities.mig.CreateMIGRequest;

public class MigClientExtended extends MessageImplementationGuidelinesClient {

    public static final String MIG_SIMULATION_RESOURCE = "/api/1.0/migsimulation";
    public static final String LOCK_RESOURCE_FORMAT = "/api/1.0/migs/%s?webdav=%s";
    public static final String MIG_PROPOSAL_RESOURCE = "/api/1.0/migproposals";

    public MigClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public MIGEntity getMigVersionRawObject(RequestContext requestContext, String migId) {
        return executeGet(
                requestContext,
                format(MIG_RESOURCE_BY_ID, migId),
                MessageImplementationGuidelineMapper::fromJsonString);
    }

    public String getDocumentation(MIGEntity migEntity, String documentationId) {
        String docStr = migEntity.getDocumentationArtifacts().get(documentationId);

        if (docStr == null) {
            throw new IllegalArgumentException("No documentation found for the specified id: " + documentationId);
        }

        return docStr;
    }

    public String createMIG(RequestContext requestContext, CreateMIGRequest migRequest) {
        return executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                MIG_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<CreateMIGRequest> requestEntity = new HttpEntity<>(migRequest, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MIG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });
    }

    public CreateMIGRequest buildMIGCreateRequest(
            String migVersion,
            String name,
            String summary,
            String messageTemplateId,
            String messageTemplateTag,
            String typeSystemAcronym,
            String typeSystemId,
            String versionId,
            String messageTemplateName,
            String direction, // In, Out, Both
            ArrayList<CreateMIGRequest.OwnBusinessContext> ownBusinessContext) {
        CreateMIGRequest request = new CreateMIGRequest();
        request.setIdentification(new CreateMIGRequest.Identification(migVersion));
        request.setDirection(direction);
        request.setStatus("Draft");

        CreateMIGRequest.Documentation documentation = new CreateMIGRequest.Documentation();
        CreateMIGRequest.Name docName = new CreateMIGRequest.Name();
        CreateMIGRequest.ArtifactValue nameArtifact = new CreateMIGRequest.ArtifactValue();
        nameArtifact.setId(generateGUID());
        nameArtifact.setVertexGUID("UID-");
        nameArtifact.setLanguageCode("en-us");
        docName.setArtifactValue(nameArtifact);
        documentation.setName(docName);

        CreateMIGRequest.Summary docSummary = new CreateMIGRequest.Summary();
        CreateMIGRequest.ArtifactValue summaryArtifact = new CreateMIGRequest.ArtifactValue();
        summaryArtifact.setId(generateGUID());
        summaryArtifact.setVertexGUID("UID-");
        summaryArtifact.setLanguageCode("en-us");
        docSummary.setArtifactValue(summaryArtifact);
        documentation.setSummary(docSummary);
        request.setDocumentation(documentation);

        CreateMIGRequest.MessageTemplate messageTemplate = new CreateMIGRequest.MessageTemplate();
        messageTemplate.setObjectGUID("");
        messageTemplate.setCustomObject(false);
        messageTemplate.setId(messageTemplateId);
        messageTemplate.setTag(messageTemplateTag);
        messageTemplate.setTypeSystemAcronym(typeSystemAcronym);
        messageTemplate.setTypeSystemId(typeSystemId);
        messageTemplate.setVersionId(versionId);

        CreateMIGRequest.Documentation mtDocumentation = new CreateMIGRequest.Documentation();
        CreateMIGRequest.Name mtName = new CreateMIGRequest.Name();
        CreateMIGRequest.BaseArtifactValue mtBaseArtifact = new CreateMIGRequest.BaseArtifactValue();
        mtBaseArtifact.setId(messageTemplateName);
        mtName.setBaseArtifactValue(mtBaseArtifact);
        mtDocumentation.setName(mtName);
        messageTemplate.setDocumentation(mtDocumentation);
        messageTemplate.setDisplayTag(messageTemplateTag);
        messageTemplate.setVersionAcronym(typeSystemAcronym);
        request.setMessageTemplate(messageTemplate);

        request.setOwnBusinessContext(ownBusinessContext);
        request.setPartnerBusinessContext(new ArrayList<>());

        Map<String, String> documentationArtifacts = new HashMap<>();
        documentationArtifacts.put(nameArtifact.getId(), name);
        documentationArtifacts.put(summaryArtifact.getId(), summary);
        request.setDocumentationArtifacts(documentationArtifacts);

        return request;
    }

    public String getMIGSimulation(RequestContext requestContext, String migId, String payload) {
        return executeMethod(requestContext, PATH_FOR_TOKEN, MIG_SIMULATION_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> requestEntity = new HttpEntity<>(format("{ObjectGuid: \"%s\", Payload: \"%s\"}", migId, payload), httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MIG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });
    }

    public String getMIGProposal(RequestContext requestContext, String migId) {
        MIGEntity currentEntity = getMigVersionRawObject(requestContext, migId);
        MigEntityUtils migUtils = new MigEntityUtils(currentEntity);
        //toggleLockMig(requestContext, migId, true);
        return executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                MIG_PROPOSAL_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<MIGProposalRequest> requestEntity = new HttpEntity<>(migUtils.getMIGProposalRequest(), httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    //toggleLockMig(requestContext, migId, false);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MIG proposal. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });

    }

    public String changeNodeSelection(RequestContext requestContext, List<String> addToSelection, List<String> removeSelection, String migId) {
        MIGEntity currentEntity = getMigVersionRawObject(requestContext, migId);
        MigEntityUtils migUtils = new MigEntityUtils(currentEntity);

        for (String addId : addToSelection) {
            migUtils.findAndChangeNodeSelection(addId, true);
        }

        for (String removeId : removeSelection) {
            migUtils.findAndChangeNodeSelection(removeId, false);
        }

        return updateMIG(requestContext, migId, migUtils.getEntity());
    }

    public String updateMIG(RequestContext requestContext, String migId, MIGEntity entity) {
        toggleLockMig(requestContext, migId, true);

        return executeMethod(requestContext, PATH_FOR_TOKEN, format(MIG_RESOURCE_BY_ID, migId),
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.set("Content-Encoding", "application/x-zip");
                    HttpEntity<String> requestEntity = new HttpEntity<>(JsonUtils.createZippedBase64(entity, "mig.json"), httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.PUT, requestEntity, String.class);
                    toggleLockMig(requestContext, migId, false);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MIG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });
    }

    private String generateGUID() {
        UUID uuid = UUID.randomUUID();
        // Format it with the "UID-" prefix
        return "UID-" + uuid.toString();
    }

    /**
     * Lock or onlock a MIG
     * @param requestContext
     * @param migId
     * @param lock False: unlocks, True: locks the artifact
     */
    private void toggleLockMig(RequestContext requestContext, String migId, boolean lock) {
        String lockUrl = format(LOCK_RESOURCE_FORMAT, migId, lock ? "LOCK" : "UNLOCK");

        executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                lockUrl,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.PUT, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't lock MIG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });

    }
}

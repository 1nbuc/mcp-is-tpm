package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.TpmBaseClient;
import de.contriboot.mcptpm.api.entities.mag.*;
import de.contriboot.mcptpm.api.entities.mapper.MAGResponseMapper;
import de.contriboot.mcptpm.api.entities.mapper.MAGSimulationResponseMapper;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import org.apache.hc.core5.annotation.Experimental;
import org.springframework.http.*;

import java.util.*;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

public class MappingGuidelineClient extends TpmBaseClient {
    private static final String MAPPING_GUIDELINE_RESOURCE = "/api/1.0/mags";
    private static final String MAPPING_GUIDELINE_BY_ID_RESOURCE = "/api/1.0/mags/%s";
    private static final String MAPPING_GUIDELINE_PROPOSAL_RESOURCE = "/api/1.0/magproposal";
    private static final String MAPPING_GUIDELINE_SIMULATION_RESOURCE = "/api/1.0/magsimulation";


    public MappingGuidelineClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public String updateMappingGuideline() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public MAGEntity getMappingGuidelineRawObject(RequestContext requestContext, String magVersionId) {
        return executeGet(
                requestContext,
                format(MAPPING_GUIDELINE_BY_ID_RESOURCE, magVersionId),
                MAGResponseMapper::fromJsonString
        );
    }



    public String createMappingGuideline(
            RequestContext requestContext,
            String sourceTypeSystemId,
            String sourceMigGUID,
            String targetTypeSystemId,
            String targetMigGUID,
            String name,
            List<MIGEntity.BusinessContext> sourceBusinessContext,
            List<MIGEntity.BusinessContext> targetBusinessContext
    ) {
        MAGCreateEntity entity = createMappingGuidelineEntity(sourceTypeSystemId, sourceMigGUID, targetTypeSystemId, targetMigGUID, name, sourceBusinessContext, targetBusinessContext);
        return executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                MAPPING_GUIDELINE_RESOURCE,
                (url, token, restTemplateWrapper) -> {
            HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MAGCreateEntity> requestEntity = new HttpEntity<>(entity, httpHeaders);
            ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                    HttpMethod.POST, requestEntity, String.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new ClientIntegrationException(format(
                        "Couldn't create MAG. Code: %d, Message: %s",
                        responseEntity.getStatusCode().value(),
                        requestEntity.getBody()));
            }

            return responseEntity.getBody();
        });
    }

    public MAGCreateEntity createMappingGuidelineEntity(
            String sourceTypeSystemId,
            String sourceMigGUID,
            String targetTypeSystemId,
            String targetMigGUID,
            String name,
            List<MIGEntity.BusinessContext> sourceBusinessContext,
            List<MIGEntity.BusinessContext> targetBusinessContext
    ) {
        MAGCreateEntity entity = new MAGCreateEntity();
        entity.setArtifactMetadata(new MAGCreateEntity.ArtifactMetadata("1.1", "MagDetail"));
        entity.setIdentification(new MAGCreateEntity.Identification("1.0", ""));
        entity.setStatus("Draft");

        String nameId = generateGUID();
        String summaryId = generateGUID();
        String definitionId = generateGUID();

        MAGCreateEntity.Documentation documentation = new MAGCreateEntity.Documentation();
        documentation.setName(new MAGCreateEntity.ArtifactValue(nameId));
        documentation.setSummary(new MAGCreateEntity.ArtifactValue(summaryId));
        documentation.setDefinition(new MAGCreateEntity.ArtifactValue(definitionId));
        documentation.setNotes(new ArrayList<>());
        documentation.setNumberOfNotes(0);
        entity.setDocumentation(documentation);

        entity.setSourceMig(new MAGCreateEntity.SourceMig(sourceMigGUID, sourceTypeSystemId));
        entity.setTargetMig(new MAGCreateEntity.TargetMig(targetMigGUID, targetTypeSystemId));

        Map<String, String> documentationArtifacts = new HashMap<>();
        documentationArtifacts.put(nameId, name);
        documentationArtifacts.put(summaryId, "");
        documentationArtifacts.put(definitionId, "");
        entity.setDocumentationArtifacts(documentationArtifacts);

        entity.setSourceBusinessContext(sourceBusinessContext);
        entity.setTargetBusinessContext(targetBusinessContext);

        entity.setDomainMappingElementsWithTransformation(new ArrayList<>());

        // TODO: add missing fields
        return entity;

    }

    @Experimental
    public String getMAGProposal(RequestContext requestContext, MAGProposalRequest requestData) {
        return executeMethod(requestContext, PATH_FOR_TOKEN, MAPPING_GUIDELINE_PROPOSAL_RESOURCE,

                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<MAGProposalRequest> requestEntity = new HttpEntity<>(requestData, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't get MAG Proposal. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });
    }

    public String simulatePayloadAgainstMAG(RequestContext requestContext, String magVersionId, String payload) {
        MAGEntity currentEntity = getMappingGuidelineRawObject(requestContext, magVersionId);

        MAGSimulationRequest magSimReq = new MAGSimulationRequest();
        magSimReq.setMag(currentEntity);
        magSimReq.setPayload(payload);

        MAGSimulationResponse response = executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                MAPPING_GUIDELINE_SIMULATION_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<MAGSimulationRequest> requestEntity = new HttpEntity<>(magSimReq, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MAG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return MAGSimulationResponseMapper.fromJsonString(responseEntity.getBody());
                });

        return response.getTargetPayload();
    }

    public String getMAGMetadata(RequestContext requestContext) {
        return executeGet(requestContext, MAPPING_GUIDELINE_RESOURCE, (response) -> response);
    }

    private String generateGUID() {
        UUID uuid = UUID.randomUUID();
        // Format it with the "UID-" prefix
        return "UID-" + uuid.toString();
    }
}

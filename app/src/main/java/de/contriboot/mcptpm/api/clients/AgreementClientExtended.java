package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import de.contriboot.mcptpm.api.entities.AgreementEntitiy;
import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;
import de.contriboot.mcptpm.api.entities.deploy.CreateDeployRequest;
import de.contriboot.mcptpm.api.entities.deploy.GetDeployStatusEntity;
import de.contriboot.mcptpm.api.entities.mapper.DeployArtifactMapper;
import de.contriboot.mcptpm.api.entities.shared.*;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.http.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

public class AgreementClientExtended extends AgreementClient {
    public static final String ACTIVATE_RESOURCE_STATUS_FORMAT = "/itspaces/tpm/api/2.0/tasks.atomic/DEPLOY_V2::%s";
    public static final String UPDATE_RESOURCE_STATUS_FORMAT = "/itspaces/tpm/api/2.0/tasks.atomic/REDEPLOY_V2::%s";
    public static final String UNDEPLOY_RESOURCE_FORMAT = "/itspaces/tpm/api/2.0/tasks.atomic/UNDEPLOY_V2::%s";
    public static final String CRETE_DEPLOYMENT = "/itspaces/tpm/api/2.0/tasks.atomic";
    public static final String ARTIFACT_TYPE = "TRADING_PARTNER_AGREEMENT_V2";


    public AgreementClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public AgreementEntitiy createAgreement(RequestContext requestContext, AgreementEntitiy agreementEntitiy) {
        return executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                AGREEMENT_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<AgreementEntitiy> requestEntity = new HttpEntity<>(agreementEntitiy, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create Agreement. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody())
                        );
                    }


                    return requestEntity.getBody();
                });

    }

    public String updateAgreement() {
        return "";
    }

    public AgreementEntitiy buildNewAgreementEntityByBoundTemplate(
            String agreementName,
            String description,
            String agreementTemplateId,
            List<String> transactionIds,
            String partnerId
    ) {
        AgreementTemplateClientExtended agreementTemplateClient = new AgreementTemplateClientExtended(new HttpClientsFactory());

        AgreementEntitiy agreementEntitiy = new AgreementEntitiy();
        agreementEntitiy.setName(agreementName);
        agreementEntitiy.setDescription(description);
        agreementEntitiy.setParentId(agreementTemplateId);
        agreementEntitiy.setShared(false);
        agreementEntitiy.setVersion("1.0");
        agreementEntitiy.setOwnedBy("Self");

        AgreementTemplateEntity agreementTemplate = agreementTemplateClient.getAgreementTemplateRawObject(Config.getRequestContextFromEnv(), agreementTemplateId);
        agreementEntitiy.setCompanyData(agreementTemplate.getCompanyData());

        TransactionOption transactionOption = new TransactionOption();
        transactionOption.setTransactionIds(transactionIds);
        transactionOption.setOption("Reference");
        agreementEntitiy.setTransactionOption(transactionOption);

        agreementEntitiy.setCompanyData(agreementTemplate.getCompanyData());

        agreementEntitiy.setTradingPartnerData(agreementTemplate.getTradingPartnerData());

        TradingPartnerDetails tradingPartnerDetails = new TradingPartnerDetails();
        IdReference idReferenceForTradingPartner = new IdReference();

        IdProperties idForTradingPartner = new IdProperties();
        idForTradingPartner.setId(partnerId);
        idReferenceForTradingPartner.setProperties(idForTradingPartner);

        tradingPartnerDetails.setIdForTradingPartner(idReferenceForTradingPartner);

        TradingPartnerClientExtended tradingPartnerClient = new TradingPartnerClientExtended(new HttpClientsFactory());
        List<PartnerSystemEntity> availablePartnerSystems = tradingPartnerClient.getSystemsOfPartner(Config.getRequestContextFromEnv(), partnerId);

        // For Template binding method there has to be a system with the specified alias, otherwise creation won't work
        String partnerSystemAlias = agreementTemplate.getTradingPartnerData()
                .getAliasForSystemInstance()
                .getProperties()
                .getAlias();
        PartnerSystemEntity matchingSystem;
        try {
            matchingSystem = availablePartnerSystems
                    .stream()
                    .filter(
                            p -> p.getAlias()
                                    .equals(partnerSystemAlias))
                    .findFirst()
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("No partner system matches the specified alias in the template: " + partnerSystemAlias);
        }


        SystemInstanceReference systemInstanceReference = new SystemInstanceReference();
        IdProperties idForSystemInstance = new IdProperties();
        idForSystemInstance.setId(matchingSystem.getId());
        idForSystemInstance.setLabelPurpose(matchingSystem.getPurpose());
        systemInstanceReference.setProperties(idForSystemInstance);

        tradingPartnerDetails.setIdForSystemInstance(systemInstanceReference);

        agreementEntitiy.setTradingPartnerDetails(tradingPartnerDetails);

        // TODO: IdForSenderIdentifier, IdForReceiverIdentifier, IdForContactPerson

        agreementEntitiy.setSourceTemplateName(agreementTemplate.getName());

        agreementEntitiy.setB2bScenarioDetailsId(agreementTemplate.getB2bScenarioDetailsId());

        agreementEntitiy.setTransactionsNumber(0);

        return agreementEntitiy;
    }

    /**
     * Activate or update an Agreement
     * Usually also means deploy in TPM context
     *
     * @param requestContext
     * @param artifactId
     * @param transactionIds If it is an update, and you want to deploy The B2B Scenario transaction. Include their IDs here
     * @return
     */
    public GetDeployStatusEntity deployArtifactAndWait(RequestContext requestContext, String artifactId, List<String> transactionIds, boolean isUpdate) {
        CreateDeployRequest requestPayload = new CreateDeployRequest();

        String deployUrl = isUpdate ? format(UPDATE_RESOURCE_STATUS_FORMAT, artifactId) : format(ACTIVATE_RESOURCE_STATUS_FORMAT, artifactId);

        CreateDeployRequest.TaskInput taskInput = new CreateDeployRequest.TaskInput();

        requestPayload.setAction(isUpdate ? "REDEPLOY_V2" : "DEPLOY_V2");
        requestPayload.setDescription("");

        taskInput.setId(artifactId);
        taskInput.setArtifactType(ARTIFACT_TYPE);
        taskInput.setDisplayName("");
        taskInput.setSemanticVersion("2.0");
        taskInput.setUniqueId("");
        requestPayload.setTaskInput(taskInput);

        requestPayload.setTaskParameters(new CreateDeployRequest.TaskParameters(transactionIds));


        requestPayload.setTaskInput(taskInput);

        GetDeployStatusEntity statusEntity = executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                CRETE_DEPLOYMENT,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<CreateDeployRequest> requestEntity = new HttpEntity<>(requestPayload, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't deploy artifact. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody())
                        );
                    }

                    return DeployArtifactMapper.fromJsonString(responseEntity.getBody());
                }
        );

        while (statusEntity.getExecutionStatus().getStatus().equals("IN_PROCESS")) {
            try {
                Thread.sleep(500);
                statusEntity = executeGet(requestContext, deployUrl, DeployArtifactMapper::fromJsonString);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return statusEntity;
    }


}

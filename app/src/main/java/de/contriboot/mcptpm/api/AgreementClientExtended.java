package de.contriboot.mcptpm.api;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import com.figaf.integration.tpm.client.agreement.AgreementTemplateClient;
import de.contriboot.mcptpm.api.entities.AgreementEntitiy;
import de.contriboot.mcptpm.api.entities.B2BScenarioEntity;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.http.*;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

public class AgreementClientExtended extends AgreementClient {
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

    public AgreementEntitiy buildNewAgreementEntity(
            String agreementName,
            String description,
            String agreementTemplateId,
            String transactionId,
            String partnerId,
            String myCompanyOrSubsidiaryId,
            boolean bindTemplate,
            String partnerSystemId,
            String mySystemId
    ) {
        AgreementTemplateClientExtended agreementTemplateClient = new AgreementTemplateClientExtended(new HttpClientsFactory());

        AgreementEntitiy agreementEntitiy = new AgreementEntitiy();
        agreementEntitiy.setName(agreementName);
        agreementEntitiy.setDescription(description);
        agreementEntitiy.setParentId(agreementTemplateId);

        if (bindTemplate) {
            agreementTemplateClient.getAgreementTemplateRawObject(Config.getRequestContextFromEnv(), agreementTemplateId);
        }

        return agreementEntitiy;
    }
}

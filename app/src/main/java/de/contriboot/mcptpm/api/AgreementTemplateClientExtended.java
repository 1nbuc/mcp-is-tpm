package de.contriboot.mcptpm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementTemplateClient;
import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;
import de.contriboot.mcptpm.api.entities.mapper.AgreementTemplateMapper;

import static java.lang.String.format;

public class AgreementTemplateClientExtended extends AgreementTemplateClient {
    protected static final String AGREEMENT_TEMPLATE_RESOURCE_BY_ID = "/itspaces/tpm/api/2.0/agreementtemplates/%s";

    public AgreementTemplateClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public AgreementTemplateEntity getAgreementTemplateRawObject(RequestContext requestContext, String agreementTemplateId) {
        ObjectMapper objectMapper = new ObjectMapper();
        return executeGet(
                requestContext,
                format(AGREEMENT_TEMPLATE_RESOURCE_BY_ID, agreementTemplateId),
                AgreementTemplateMapper::fromJsonString
        );
    }
}

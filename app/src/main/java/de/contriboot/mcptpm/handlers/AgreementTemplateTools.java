package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementTemplateClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.api.AgreementTemplateClientExtended;
import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementTemplateTools {

    private AgreementTemplateClientExtended client;

    public AgreementTemplateTools() {
        this.client = new AgreementTemplateClientExtended(new HttpClientsFactory());
    }

    @Tool(name = "get-all-agreement-template-metadata", description = "Get metadata for all agreement templates.")
    public List<TpmObjectMetadata> getAllAgreementTemplateMetadata() {
        return client.getAllMetadata(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-agreement-template", description = "Get all details for a agreement template")
    public AgreementTemplateEntity getAgreementTemplate(String agreementTemplateId) {
        return client.getAgreementTemplateRawObject(Config.getRequestContextFromEnv(), agreementTemplateId);
    }
}

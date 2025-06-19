package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementTemplateClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementTemplateTools {

    private AgreementTemplateClient client;

    public AgreementTemplateTools() {
        this.client = new AgreementTemplateClient(new HttpClientsFactory());
    }

    @Tool(name = "get-all-agreement-template-metadata", description = "Get metadata for all agreement templates.")
    public List<TpmObjectMetadata> getAllAgreementTemplateMetadata() {
        return client.getAllMetadata(Config.getRequestContextFromEnv());
    }
}

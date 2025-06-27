package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.company.CompanyProfileClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyProfileTools {

    private final CompanyProfileClient client;

    public CompanyProfileTools() {
        this.client = new CompanyProfileClient(new HttpClientsFactory());
    }

    @Tool(name = "get-all-company-profile-metadata", description = "Get metadata for all company profiles.")
    public List<TpmObjectMetadata> getAllCompanyProfileMetadata() {
        return client.getAllMetadata(Config.getRequestContextFromEnv());
    }
}

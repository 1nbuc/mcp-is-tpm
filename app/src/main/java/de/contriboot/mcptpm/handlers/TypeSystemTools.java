package de.contriboot.mcptpm.handlers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.figaf.integration.common.factory.HttpClientsFactory;

import de.contriboot.mcptpm.api.clients.TypeSystemClient;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;
import de.contriboot.mcptpm.utils.Config;

@Service
public class TypeSystemTools {
    private TypeSystemClient client;

    public TypeSystemTools() {
        this.client = new TypeSystemClient(new HttpClientsFactory());
    }

    @Tool(name = "get-type-systems", description = "Get available type systems")
    public List<AllTypeSystemsResponse> getTypeSystems() {
        return client.getTypeSystems(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-type-system-messages", description = "Get messages of type system")
    public JsonNode getTypeSystemMessage(String typeSystemId) {
        return ToolUtils.parseJson(
                client.getTypeSystemMessagesRaw(Config.getRequestContextFromEnv(), typeSystemId)
        );
    }

    @Tool(name = "get-all-business-processes")
    public JsonNode getAllBusinessProcesses() {
        return ToolUtils.parseJson(
                client.getAllBusinessProcesses(Config.getRequestContextFromEnv())
        );
    }

    @Tool(name = "get-all-industry-classifications")
    public JsonNode getAllIndustryClassifications() {
        return ToolUtils.parseJson(
                client.getAllIndustryClassifications(Config.getRequestContextFromEnv())
        );
    }

    @Tool(name = "get-all-product-classifications")
    public JsonNode getAllProductClassifications() {
        return ToolUtils.parseJson(
                client.getAllProductClassifications(Config.getRequestContextFromEnv())
        );
    }

    @Tool(name = "get-all-contries-or-regions")
    public JsonNode getAllCountries() {
        return ToolUtils.parseJson(
                client.getAllCountries(Config.getRequestContextFromEnv())
        );
    }

    @Tool(name = "get-all-business-process-roles")
    public JsonNode getAllBusinessProcessRoles() {
        return ToolUtils.parseJson(
                client.getAllBusinessProcessRoles(Config.getRequestContextFromEnv())
        );
    }
}

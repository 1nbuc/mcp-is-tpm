package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.figaf.integration.common.factory.HttpClientsFactory;
import de.contriboot.mcptpm.api.clients.TypeSystemClient;
import de.contriboot.mcptpm.api.entities.mapper.TypeSystemMessagesMapper;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeSystemTools {
    private final TypeSystemClient client;

    public TypeSystemTools() {
        this.client = new TypeSystemClient(new HttpClientsFactory());
    }

    @Tool(name = "get-type-systems", description = "Get available type systems. " +
            "Every System also has a custom type system which is not listed here. The ID of this system is Customer_TS. " +
            "You can query messages for this system as for every other")
    public List<AllTypeSystemsResponse> getTypeSystems() {
        return client.getTypeSystems(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-type-system-messages", description = "Get messages of a type system. To query custom messages use ID Customer_TS")
    public JsonNode getTypeSystemMessage(String typeSystemId) {
        return TypeSystemMessagesMapper.fromJsonStringMinified(
                client.getTypeSystemMessagesRaw(Config.getRequestContextFromEnv(), typeSystemId)
        );
    }

    @Tool(name = "create-custom-message", description = "Create a custom message in typesystem Customer_TS based on XSD")
    JsonNode createCustomMessage(
            @ToolParam(description = "identifier within the type system") String messageName,
            @ToolParam(description = "Name of the root element for the message") String rootElementName,
            @ToolParam(description = "Namespace of the message, for no NS pass empty string") String namespace,
            @ToolParam(description = "XSD Definition of the message") String xsd
    ) {
        return ToolUtils.parseJson(
                client.createCustomMessage(
                        Config.getRequestContextFromEnv(),
                        messageName,
                        xsd,
                        rootElementName,
                        namespace
                ));
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

    @Tool(name = "get-type-system-message-full", description = "Get a message fom a type system with all details including versions and revisions")
    public JsonNode getTypeSystemMessageVersions(String typeSystemId, String messageVertexGUID) {
        JsonNode allMessages = ToolUtils.parseJson(client.getTypeSystemMessagesRaw(Config.getRequestContextFromEnv(), typeSystemId));

        ArrayNode allMessageList = (ArrayNode) allMessages.get("Message");

        for (JsonNode message : allMessageList) {
            if (message.get("VertexGUID").asText().equals(messageVertexGUID)) {
                return message;
            }
        }

        throw new IllegalStateException("No message found with GUID: " + messageVertexGUID);
    }

    @Tool(name = "get-qualifiers-codelist", description = "Get codelist of a qualifier")
    public JsonNode getQualifierCodelist(String typesystem, String version, String qualifier) {
        return client.getCodelistValues(Config.getRequestContextFromEnv(), typesystem, version, qualifier);
    }
}

package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.trading.TradingPartnerClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import com.figaf.integration.tpm.entity.trading.*;
import com.figaf.integration.tpm.entity.trading.verbose.TradingPartnerVerboseDto;
import de.contriboot.mcptpm.handlers.base.Schemas;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.TestArg;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerTools  {

    private TradingPartnerClient client;
    private JsonSchemaGenerator schemaGenerator = new JsonSchemaGenerator(new ObjectMapper());

    public PartnerTools() {
        this.client = new TradingPartnerClient(new HttpClientsFactory());
    }

    @Tool(name = "get-partner-metadata", description = "Get all trading partners")
    public List<TpmObjectMetadata> getAllPartners() {
        return client.getAllMetadata(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-receiver-adapters", description = "Get all receiver adapters of trading partner systems")
    public List<Adapter> getAllReceiverAdapters() {
        return client.getReceiverAdapters(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-sender-adapters", description = "Get all sender adapters of trading partner systems")
    public List<Adapter> getAllSenderAdapters() {
        return client.getSenderAdapters(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-all-products", description = "Get all available products/types for a system e.g. SAP SuccessFactors etc.")
    public List<Product> getAllProducts() {
        return client.getAllProducts(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-partner", description = "Get partner details by partner id")
    public String getPartner(@ToolParam(description = "Id of the partner") TestArg arg, String  partnerId) {
        return client.getRawById(partnerId, Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-type-systems", description = "Get available type systems")
    public List<TypeSystem> getAllTypeSystems() {
        return client.getAllTypeSystems(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-system-types", description = "Get available system types")
    public List<TypeSystem> getAllSystemTypes() {
        return client.getAllTypeSystems(Config.getRequestContextFromEnv());
    }

    @Tool(name = "create-system", description = "Create system of a trading partner can include 0-N type systems and " +
            "communication channels. You can create the system first then use create-communication for channels")
    public String createSystem(
            @ToolParam(required = true, description = "") String partnerId,
            @ToolParam(required = false, description = "") String alias,
            @ToolParam(required = true, description = "") String id,
            @ToolParam(required = true, description = "") String name,
            @ToolParam(required = false, description = "") String link,
            @ToolParam(required = false, description = "") String purpose,
            @ToolParam(required = false, description = "") String description) {
        CreateSystemRequest request = new CreateSystemRequest();
        request.setAlias(alias);
        request.setId(id);
        request.setName(name);
        request.setLink(link);
        request.setPurpose(purpose);
        request.setCommunicationChannelTemplates();
        request.setDescription(description);
        request.setSystemType();
        request.setTypeSystems();

        return client.createSystem(partnerId, request, Config.getRequestContextFromEnv());
    }

    @Tool(name = "create-system", description = "Create system of a trading partner can include 0-N type systems and " +
            "communication channels. You can create the system first then use create-communication for channels", schema = CreateSystemRequest.class)
    public String createSystem(CreateSystemRequest request) {
        return client.createSystem("", request, Config.getRequestContextFromEnv());
    }

    @Tool(name = "create-communication", description = "Create communication channel of system of a trading partner")
    public String createCommunication(String partnerId, String systemId, CreateCommunicationRequest request) {
        client.createCommunication(partnerId, systemId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "create-identifier", description = "Create partner Identifier")
    public String createIdentifier(String partnerId, CreateIdentifierRequest request) {
        client.createIdentifier(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "create-signature-verify-config", description = "Create Signature Verification configuration for partner")
    public String createSignatureVerifyConfig(String partnerId, CreateSignatureVerificationConfigurationsRequest request) {
        client.createSignatureVerificationConfigurationsRequest(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

}

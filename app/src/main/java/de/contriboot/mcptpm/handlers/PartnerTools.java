package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.trading.TradingPartnerClient;
import com.figaf.integration.tpm.entity.trading.System;
import com.figaf.integration.tpm.entity.trading.verbose.TradingPartnerVerboseDto;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import com.figaf.integration.tpm.entity.trading.*;
import de.contriboot.mcptpm.api.clients.TradingPartnerClientExtended;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartnerTools  {

    private TradingPartnerClientExtended client;
    private ObjectMapper objectMapper = new ObjectMapper(); // For JSON deserialization

    public PartnerTools() {
        this.client = new TradingPartnerClientExtended(new HttpClientsFactory());
        // this.schemaGenerator = new JsonSchemaGenerator(new ObjectMapper()); // Not directly used for this refactoring
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
    public String getPartner(@ToolParam(description = "Id of the partner") String partnerId) {
        return client.getRawById(partnerId, Config.getRequestContextFromEnv());
    }


    @Tool(name = "get-system-types", description = "Get available system types")
    public List<SystemType> getAllSystemTypes() { // Corrected client call
        return client.getAllSystemTypes(Config.getRequestContextFromEnv());
    }

    @Tool(name = "create-system", description = "Create system of a trading partner. Can include 0-N type systems and communication channels. You can create the system first then use create-communication for channels")
    public System createSystem(
            @ToolParam(description = "ID of the trading partner to associate this system with") String partnerId,
            @ToolParam(description = "ID for the new system") String id,
            @ToolParam(description = "Name for the new system") String name,
            @ToolParam(description = "Alias for the system", required = false) String alias,
            @ToolParam(description = "Description for the system", required = false) String description,
            @ToolParam(description = "System type", required = false) String systemType,
            @ToolParam(description = "Purpose of the system. Can be Dev, Test, Prod", required = true) String purpose,
            @ToolParam(description = "Link associated with the system", required = false) String link,
            @ToolParam(description = "JSON list of type systems. Each element is a JSON string representing a TypeSystem object (e.g., {\"Id\":\"sysId\", \"Name\":\"sysName\", \"Versions\":[{\"Id\":\"vId\", \"Name\":\"vName\"}]})", required = false) List<String> typeSystemsJsonList,
            @ToolParam(description = "List of communication channel template IDs", required = false) List<String> communicationChannelTemplates) {
        CreateSystemRequest request = new CreateSystemRequest();
        request.setId(id);
        request.setName(name);
        if (alias != null) request.setAlias(alias);
        if (description != null) request.setDescription(description);
        if (systemType != null) request.setSystemType(systemType);
        if (purpose != null) request.setPurpose(purpose);
        if (link != null) request.setLink(link);

        if (typeSystemsJsonList != null && !typeSystemsJsonList.isEmpty()) {
            List<CreateSystemRequest.TypeSystem> parsedTypeSystems = new ArrayList<>();
            for (String json : typeSystemsJsonList) {
                try {
                    parsedTypeSystems.add(objectMapper.readValue(json, CreateSystemRequest.TypeSystem.class));
                } catch (IOException e) {
                    throw new RuntimeException("Error parsing TypeSystem JSON: " + json, e);
                }
            }
            request.setTypeSystems(parsedTypeSystems);
        } else {
            request.setTypeSystems(new ArrayList<CreateSystemRequest.TypeSystem>()); // Explicit generic type
        }

        if (communicationChannelTemplates != null) {
            request.setCommunicationChannelTemplates(communicationChannelTemplates);
        } else {
            request.setCommunicationChannelTemplates(new ArrayList<String>()); // Explicit generic type
        }

        return client.createSystem(partnerId, request, Config.getRequestContextFromEnv());
    }

    // Removed the overloaded createSystem(CreateSystemRequest request) method

    @Tool(name = "create-communication", description = "Create communication channel for a system of a trading partner")
    public String createCommunication(
            @ToolParam(description = "ID of the trading partner") String partnerId,
            @ToolParam(description = "ID of the system within the trading partner") String systemId,
            @ToolParam(description = "Direction of the communication (e.g., Inbound, Outbound)", required = false) String direction,
            @ToolParam(description = "Adapter type (e.g., AS2, IDOC)", required = false) String adapterType,
            @ToolParam(description = "AS2 Partner ID, if applicable", required = false) String as2PartnerId,
            @ToolParam(description = "Security configuration mode", required = false) String securityConfigurationMode,
            @ToolParam(description = "Name of the communication channel") String name,
            @ToolParam(description = "Alias for the communication channel", required = false) String alias,
            @ToolParam(description = "Description for the communication channel", required = false) String description,
            @ToolParam(description = "JSON string representing configuration properties (Map<String, Attribute> e.g. {\"prop1\":{\"key\":\"prop1\",\"value\":\"val1\",\"isPersisted\":true}})", required = false) String configurationPropertiesJson) {
        CreateCommunicationRequest request = new CreateCommunicationRequest();
        if (direction != null) request.setDirection(direction);
        if (adapterType != null) request.setAdapterType(adapterType);
        if (as2PartnerId != null) request.setAs2PartnerId(as2PartnerId);
        if (securityConfigurationMode != null) request.setSecurityConfigurationMode(securityConfigurationMode);
        request.setName(name);
        if (alias != null) request.setAlias(alias);
        if (description != null) request.setDescription(description);

        if (configurationPropertiesJson != null && !configurationPropertiesJson.isEmpty()) {
            try {
                TypeReference<HashMap<String, CreateCommunicationRequest.Attribute>> typeRef = new TypeReference<HashMap<String, CreateCommunicationRequest.Attribute>>() {};
                Map<String, CreateCommunicationRequest.Attribute> propsMap = objectMapper.readValue(configurationPropertiesJson, typeRef);
                CreateCommunicationRequest.ConfigurationProperties configProps = new CreateCommunicationRequest.ConfigurationProperties();
                configProps.setAllAttributes(propsMap);
                request.setConfigurationProperties(configProps);
            } catch (IOException e) {
                throw new RuntimeException("Error parsing ConfigurationProperties JSON: " + configurationPropertiesJson, e);
            }
        } else {
            request.setConfigurationProperties(new CreateCommunicationRequest.ConfigurationProperties());
        }

        client.createCommunication(partnerId, systemId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "create-identifier", description = "Create a partner Identifier")
    public String createIdentifier(
            @ToolParam(description = "ID of the trading partner") String partnerId,
            @ToolParam(description = "Is this a group identifier?") boolean isGroupIdentifier,
            @ToolParam(description = "Type System ID for the identifier") String typeSystemId,
            @ToolParam(description = "Scheme code for the identifier", required = false) String schemeCode,
            @ToolParam(description = "Scheme name for the identifier", required = false) String schemeName,
            @ToolParam(description = "The identifier ID itself") String identifierId,
            @ToolParam(description = "Alias for the identifier", required = false) String alias,
            @ToolParam(description = "Agency associated with the identifier", required = false) String agency) {
        CreateIdentifierRequest request = new CreateIdentifierRequest();
        request.setGroupIdentifier(isGroupIdentifier);
        request.setTypeSystemId(typeSystemId);
        if (schemeCode != null) request.setSchemeCode(schemeCode);
        if (schemeName != null) request.setSchemeName(schemeName);
        request.setIdentifierId(identifierId);
        if (alias != null) request.setAlias(alias);
        if (agency != null) request.setAgency(agency);

        client.createIdentifier(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "create-signature-verify-config", description = "Create Signature Verification configuration for a partner")
    public String createSignatureVerifyConfig(
            @ToolParam(description = "ID of the trading partner") String partnerId,
            @ToolParam(description = "Artifact type (default: TRADING_PARTNER)", required = false) String artifactType,
            @ToolParam(description = "Verification option (default: NotRequired)", required = false) String verificationOption,
            @ToolParam(description = "AS2 Partner ID, if applicable", required = false) String as2PartnerId,
            @ToolParam(description = "Alias for the configuration", required = false) String alias,
            @ToolParam(description = "Verify MIC (Message Integrity Check)?") boolean verifyMIC,
            @ToolParam(description = "Public key alias for verification", required = false) String publicKeyAliasForVerification) {
        CreateSignatureVerificationConfigurationsRequest request = new CreateSignatureVerificationConfigurationsRequest();
        if (artifactType != null) request.setArtifactType(artifactType); else request.setArtifactType("TRADING_PARTNER"); // set default if null
        if (verificationOption != null) request.setVerificationOption(verificationOption); else request.setVerificationOption("NotRequired"); // set default if null
        if (as2PartnerId != null) request.setAs2PartnerId(as2PartnerId);
        if (alias != null) request.setAlias(alias);
        request.setVerifyMIC(verifyMIC);
        if (publicKeyAliasForVerification != null) request.setPublicKeyAliasForVerification(publicKeyAliasForVerification);

        client.createSignatureVerificationConfigurationsRequest(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "create-trading-partner", description = "Create a new trading partner")
    public TradingPartnerVerboseDto createTradingPartnerTool(
            @ToolParam(description = "Name of the trading partner") String name,
            @ToolParam(description = "Short name of the trading partner") String shortName,
            @ToolParam(description = "Web URL of the trading partner", required = false) String webURL,
            @ToolParam(description = "Logo ID for the trading partner", required = false) String logoId,
            @ToolParam(description = "Email address of the trading partner", required = false) String emailAddress,
            @ToolParam(description = "Phone number of the trading partner", required = false) String phoneNumber,
            @ToolParam(description = "JSON string representing the Profile object. See CreateTradingPartnerRequest.Profile for structure.", required = false) String profileJson,
            @ToolParam(description = "Artifact type (default: TRADING_PARTNER)", required = false) String artifactType) {
        CreateTradingPartnerRequest request = new CreateTradingPartnerRequest();
        request.setName(name);
        if (shortName != null) request.setShortName(shortName);
        if (webURL != null) request.setWebURL(webURL);
        if (logoId != null) request.setLogoId(logoId);
        if (emailAddress != null) request.setEmailAddress(emailAddress);
        if (phoneNumber != null) request.setPhoneNumber(phoneNumber);

        if (profileJson != null && !profileJson.isEmpty()) {
            try {
                CreateTradingPartnerRequest.Profile profile = objectMapper.readValue(profileJson, CreateTradingPartnerRequest.Profile.class);
                request.setProfile(profile);
            } catch (IOException e) {
                throw new RuntimeException("Error parsing Profile JSON: " + profileJson, e);
            }
        } else {
            request.setProfile(new CreateTradingPartnerRequest.Profile());
        }

        if (artifactType != null && !artifactType.isEmpty()) {
            request.setArtifactType(artifactType);
        } else {
            request.setArtifactType("TRADING_PARTNER"); // Default as in entity
        }

        return client.createTradingPartner(request, Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-systems-of-partner", description = "Returns all systems of a trading partner by its ID")
    public List<PartnerSystemEntity> getTradingPartnerSystem(String partnerId) {
        return client.getSystemsOfPartner(Config.getRequestContextFromEnv(), partnerId);
    }

}

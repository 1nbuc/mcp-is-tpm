package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import com.figaf.integration.tpm.entity.trading.*;
import com.figaf.integration.tpm.entity.trading.CreateSystemRequest.TypeSystem;
import com.figaf.integration.tpm.entity.trading.System;
import com.figaf.integration.tpm.entity.trading.verbose.TradingPartnerVerboseDto;
import de.contriboot.mcptpm.api.clients.TradingPartnerClientExtended;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartnerTools {

    private final TradingPartnerClientExtended client;
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON deserialization

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
            @ToolParam(description = "Alias for the system") String alias,
            @ToolParam(description = "Description for the system", required = false) String description,
            @ToolParam(description = "System type") String systemType,
            @ToolParam(description = "Purpose of the system. Can be Dev, Test, Prod") String purpose,
            @ToolParam(description = "Link associated with the system", required = false) String link,
            @ToolParam(description = "JSON list of type systems. Each element is a JSON string representing a TypeSystem object (e.g., {\"Id\":\"sysId\", \"Name\":\"sysName\", \"Versions\":[{\"Id\":\"vId\", \"Name\":\"vName\"}]})", required = false) List<String> typeSystemsJsonList,
            @ToolParam(description = "List of communication channel template IDs", required = false) List<String> communicationChannelTemplates,
            @ToolParam(description = "Systems supported Type systems e.g. {\"Id\":\"EANCOM\",\"Name\":\"GS1 EANCOM\",\"Versions\":[{\"Id\":\"D.19B S3\",\"Name\":\"D.19B S3\"}]}") List<TypeSystem> supportedTypeSystems) {
        CreateSystemRequest request = new CreateSystemRequest();
        request.setId(id);
        request.setName(name);
        if (alias != null) request.setAlias(alias);
        if (description != null) request.setDescription(description);
        if (systemType != null) request.setSystemType(systemType);
        if (purpose != null) request.setPurpose(purpose);
        if (link != null) request.setLink(link);
        request.setTypeSystems(supportedTypeSystems);

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
    public String createCommunication(@ToolParam(description = "ID of the trading partner") String partnerId, @ToolParam(description = "ID of the system within the trading partner") String systemId, @ToolParam(description = "Direction of the communication (either Receiver or Sender)") String direction, @ToolParam(description = "Adapter type (e.g., AS2, IDOC)", required = false) String adapterType, @ToolParam(description = "AS2 Partner ID, if applicable", required = false) String as2PartnerId, @ToolParam(description = "Security configuration mode", required = false) String securityConfigurationMode, @ToolParam(description = "Name of the communication channel") String name, @ToolParam(description = "Alias for the communication channel") String alias, @ToolParam(description = "Description for the communication channel", required = false) String description, @ToolParam(description = "JSON string representing configuration properties (Map<String, Attribute> e.g. {\"prop1\":{\"key\":\"prop1\",\"value\":\"val1\",\"isPersisted\":true}})", required = false) String configurationPropertiesJson) {
        CreateCommunicationRequest request = new CreateCommunicationRequest();
        request.setDirection(direction);
        if (adapterType != null) request.setAdapterType(adapterType);
        if (as2PartnerId != null) request.setAs2PartnerId(as2PartnerId);
        if (securityConfigurationMode != null) request.setSecurityConfigurationMode(securityConfigurationMode);
        request.setName(name);
        request.setAlias(alias);
        if (description != null) request.setDescription(description);

        if (configurationPropertiesJson != null && !configurationPropertiesJson.isEmpty()) {
            try {
                TypeReference<HashMap<String, CreateCommunicationRequest.Attribute>> typeRef = new TypeReference<HashMap<String, CreateCommunicationRequest.Attribute>>() {
                };
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

    @Tool(name = "create-identifier", description = "Create a partner Identifier. " + "For the scheme values please check tool get-type-system-identifier-schemes first. Only single identifiers are supported")
    public String createIdentifier(
            @ToolParam(description = "ID of the trading partner") String partnerId,
            @ToolParam(description = "Type System ID for the identifier") String typeSystemId,
            @ToolParam(description = "Scheme code for the identifier", required = true) String schemeCode,
            @ToolParam(description = "Scheme name for the identifier, check get-type-system-identifier-schemes", required = true) String schemeName,
            @ToolParam(description = "The identifier Name") String identifierId, @ToolParam(description = "Alias for the identifier") String alias
    ) {
        CreateIdentifierRequest request = new CreateIdentifierRequest();
        request.setGroupIdentifier(false);
        request.setTypeSystemId(typeSystemId);
        request.setSchemeCode(schemeCode);
        request.setSchemeName(schemeName);
        request.setIdentifierId(identifierId);
        request.setAlias(alias);
        request.setAgency(schemeName);

        client.createIdentifier(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "get-identifiers", description = "Get identifiers of a partner")
    public JsonNode getIdentifiers(@ToolParam(description = "ID of the trading partner") String partnerId) {
        return ToolUtils.parseJson(client.getIdentifiers(Config.getRequestContextFromEnv(), partnerId));
    }

    @Tool(name = "create-signature-verify-config", description = "Create Signature Verification configuration for a partner. "
            + "Use activate-signature-verify-config afterwards to activate the verify config " +
            "This is used most likely for AS2 systems")
    public String createSignatureVerifyConfig(
            @ToolParam(description = "ID of the trading partner") String partnerId,
            @ToolParam(description = "Artifact type (default: TRADING_PARTNER)", required = false) String artifactType,
            @ToolParam(description = "Verification option (default: NotRequired)", required = false) String verificationOption,
            @ToolParam(description = "AS2 Partner ID, if applicable", required = false) String as2PartnerId,
            @ToolParam(description = "Alias for the configuration", required = false) String alias,
            @ToolParam(description = "Verify MIC (Message Integrity Check)?") boolean verifyMIC,
            @ToolParam(description = "Public key alias for verification", required = false) String publicKeyAliasForVerification) {
        CreateSignatureVerificationConfigurationsRequest request = new CreateSignatureVerificationConfigurationsRequest();
        if (artifactType != null) request.setArtifactType(artifactType);
        else request.setArtifactType("TRADING_PARTNER"); // set default if null
        if (verificationOption != null) request.setVerificationOption(verificationOption);
        else request.setVerificationOption("NotRequired"); // set default if null
        if (as2PartnerId != null) request.setAs2PartnerId(as2PartnerId);
        if (alias != null) request.setAlias(alias);
        request.setVerifyMIC(verifyMIC);
        if (publicKeyAliasForVerification != null)
            request.setPublicKeyAliasForVerification(publicKeyAliasForVerification);

        client.createSignatureVerificationConfigurationsRequest(partnerId, request, Config.getRequestContextFromEnv());
        return "success";
    }

    @Tool(name = "activate-signature-verify-config")
    public JsonNode activateSignatureVerifyConfig(String partnerId, String as2partnerId) {
        return ToolUtils.parseJson(client.activateSignatureVerifyConfig(Config.getRequestContextFromEnv(), partnerId, as2partnerId));
    }

    @Tool(name = "create-trading-partner", description = "Create a new trading partner")
    public TradingPartnerVerboseDto createTradingPartnerTool(@ToolParam(description = "Name of the trading partner") String name, @ToolParam(description = "Short name of the trading partner") String shortName, @ToolParam(description = "Web URL of the trading partner", required = false) String webURL, @ToolParam(description = "Logo ID for the trading partner", required = false) String logoId, @ToolParam(description = "Email address of the trading partner", required = false) String emailAddress, @ToolParam(description = "Phone number of the trading partner", required = false) String phoneNumber, @ToolParam(description = "JSON string representing the Profile object. See CreateTradingPartnerRequest.Profile for structure.", required = false) String profileJson, @ToolParam(description = "Artifact type (default: TRADING_PARTNER)", required = false) String artifactType) {
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

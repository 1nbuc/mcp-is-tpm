package de.contriboot.mcptpm.api.clients;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.TpmBaseClient;

import de.contriboot.mcptpm.api.entities.deploy.CreateDeployRequest;
import de.contriboot.mcptpm.api.entities.mag.MAGCreateEntity;
import de.contriboot.mcptpm.api.entities.mapper.TypeSystemsMapper;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;
import de.contriboot.mcptpm.utils.ToolUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;

public class TypeSystemClient extends TpmBaseClient {
    public static final String TYPE_MESSAGE_VERSIONS_FORMAT = "/api/1.0/typesystems/%s?artifacttype=Message&filter=messagesOnly";
    public static final String TYPE_SYSTEMS = "/api/1.0/typesystems";
    public static final String BUSINESS_PROCESS_RESOURCE = "/api/1.0/contextdimensions/BusinessProcess";
    public static final String INDUSTRY_CLASSIFICATION_RESOURCE = "/api/1.0/contextdimensions/IndustryClassification";
    public static final String PRODUCT_CLASSIFICATION_RESOURCE = "/api/1.0/contextdimensions/ProductClassification";
    public static final String GEOPOLITICAL_RESOURCE = "/api/1.0/contextdimensions/Geopolitical";
    public static final String BUSINESS_PROCESS_ROLE_RESOURCE = "/api/1.0/contextdimensions/BusinessProcessRole";
    public static final String CODE_VALUE_FORMAT = "/api/1.0/typesystems/%s/versions/%s/codelists/%s";
    public static final String CUSTOM_TYPE_SYSTEM_MESSAGES_RESOURCE = "/api/1.0/customtypesystems/Customer_TS?artifacttype=TypeSystem";
    public static final String CUSTOM_TYPE_PROPAGATE_XSD_RESOURCE = "/api/1.0/custommessages?syntaxType=XML";
    public static final String CUSTOM_TYPE_MESSAGES_RESOURCE = "/api/1.0/custommessages";
    public static final String CREATE_TYPE_PAYLOAD_FORMAT = "------geckoformboundarya4499a09c191b0131d749e7af605418b\n" +
            "Content-Disposition: form-data; name=\"file\"; filename=\"payload.xsd\"\n" +
            "Content-Type: application/octet-stream\n" +
            "\n%s" +
            "------geckoformboundarya4499a09c191b0131d749e7af605418b\n" +
            "Content-Disposition: form-data; name=\"fileuploader-data\"\n" +
            "\n" +
            "{\"identifier\":\"%s\",\"name\":\"%s\",\"version\":\"1.0\",\"definition\":\"\",\"takenFromXSD\":false,\"tsVertexGuid\":\"%s\",\"typeSystemId\":\"Customer_TS\",\"typeSystemAcronym\":\"Custom Messages\",\"syntaxType\":\"XML\",\"selectedMessageName\":\"%s\",\"selectedMessageNamespace\":\"%s\"}\n" +
            "------geckoformboundarya4499a09c191b0131d749e7af605418b--\n";

    public TypeSystemClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public List<AllTypeSystemsResponse> getTypeSystems(RequestContext requestContext) {
        return executeGet(
                requestContext,
                TYPE_SYSTEMS,
                TypeSystemsMapper::fromJsonString
        );
    }

    // Has to be done with executeMethod because otherwise causes problems with the trial version of CPI
    public String getTypeSystemMessagesRaw(RequestContext requestContext, String typeSystem) {
        return executeMethod(requestContext, PATH_FOR_TOKEN,
                typeSystem.equals("Customer_TS") ? CUSTOM_TYPE_SYSTEM_MESSAGES_RESOURCE : format(TYPE_MESSAGE_VERSIONS_FORMAT, typeSystem),
                (url, token, restTemplateWrapper) -> {
            HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CreateDeployRequest> requestEntity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new ClientIntegrationException(format(
                        "Couldn't get type system messages. Code: %d, Message: %s",
                        responseEntity.getStatusCode().value(),
                        requestEntity.getBody())
                );
            }

            return responseEntity.getBody();
        });
    }

    @Getter
    @Builder
    private static class CustomTypeMessageRequest {
        private String identifier;
        private String name;
        @Builder.Default
        private String version = "1.0";
        @Builder.Default
        private String definition = "";
        @Builder.Default
        private boolean takenFromXSD = false;
        private String tsVertexGuid;
        @Builder.Default
        private String typeSystemId = "Customer_TS";
        @Builder.Default
        private String syntaxType = "XML";
        private String selectedMessageName;
        private String selecteMessageNamespace;

    }

    public String createCustomMessage(RequestContext requestContext, String messageName, String xsdPayload, String selectedMessageElem, String selectedMessageNamespace) {
        String tSVertexGuid = getCustomerTsVertexGUID(requestContext);

        executeMethod(requestContext, PATH_FOR_TOKEN, CUSTOM_TYPE_PROPAGATE_XSD_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    HttpEntity<String> requestEntity = new HttpEntity<>(xsdPayload, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't propagate type XSD. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });

        CustomTypeMessageRequest request = CustomTypeMessageRequest
                .builder()
                .name(messageName)
                .identifier(messageName)
                .tsVertexGuid(tSVertexGuid)
                .selectedMessageName(selectedMessageElem)
                .selecteMessageNamespace(selectedMessageNamespace == null ? "" : selectedMessageNamespace.trim()).build();

        String typePayload = format(
                CREATE_TYPE_PAYLOAD_FORMAT,
                xsdPayload.getBytes(StandardCharsets.UTF_8),
                messageName,
                messageName,
                tSVertexGuid,
                selectedMessageElem,
                selectedMessageNamespace == null ? "" : selectedMessageNamespace
        );

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        builder.part("file", xsdPayload, MediaType.APPLICATION_OCTET_STREAM).filename("payload.xsd");
        try {
            builder.part("fileuploader-data", objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return executeMethod(requestContext, PATH_FOR_TOKEN, CUSTOM_TYPE_PROPAGATE_XSD_RESOURCE,
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                    HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(builder.build(), httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't propagate type XSD. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });




    }

    private String getCustomerTsVertexGUID(RequestContext requestContext) {
        return ToolUtils
                .parseJson(getTypeSystemMessagesRaw(requestContext, "Customer_TS"))
                .get("VertexGUID")
                .asText();
    }

    public String getAllBusinessProcesses(RequestContext requestContext) {
        return executeGet(requestContext, BUSINESS_PROCESS_RESOURCE, (response) -> response );
    }

    public String getAllIndustryClassifications(RequestContext requestContext) {
        return executeGet(requestContext, INDUSTRY_CLASSIFICATION_RESOURCE, (response) -> response );
    }

    public String getAllProductClassifications(RequestContext requestContext) {
        return executeGet(requestContext, PRODUCT_CLASSIFICATION_RESOURCE, (response) -> response );
    }

    public String getAllCountries(RequestContext requestContext) {
        return executeGet(requestContext, GEOPOLITICAL_RESOURCE, (response) -> response );
    }

    public String getAllBusinessProcessRoles(RequestContext requestContext) {
        return executeGet(requestContext, BUSINESS_PROCESS_ROLE_RESOURCE, (response) -> response );
    }

    public JsonNode getCodelistValues(RequestContext requestContext, String typeSystem, String version, String codeListId) {
        String uri = format(CODE_VALUE_FORMAT, typeSystem, version, codeListId).replace(" ", "%20");

            return executeGet(
                    requestContext,
                    uri.toString(),
                    (response) -> ToolUtils.parseJson(response)
            );

    }

    public List<String> getCodeValueVertexIds(
            RequestContext requestContext,
            String typeSystem,
            String version,
            String codeListId,
            List<String> selectedCodes,
            boolean allCodesSelected
    ) {
        String uri = format(CODE_VALUE_FORMAT, typeSystem, version, codeListId).replace(" ", "%20");
        ArrayNode allCodes;
        try {
            allCodes = (ArrayNode) executeGet(
                    requestContext,
                    uri.toString(),
                    (response) -> ToolUtils.parseJson(response).get("Codes")
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }


         List<String> resultGuids = new ArrayList<>();

         for (JsonNode code : allCodes) {
             if (allCodesSelected) {
                 resultGuids.add(code.get("VertexGUID").asText());
                 continue;
             }

            if (selectedCodes.contains(code.get("Id").asText())) {
                resultGuids.add(code.get("VertexGUID").asText());
            }
         }

         return resultGuids;
    }
}

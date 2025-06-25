package de.contriboot.mcptpm.api.clients;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.TpmBaseClient;

import de.contriboot.mcptpm.api.entities.deploy.CreateDeployRequest;
import de.contriboot.mcptpm.api.entities.mapper.TypeSystemsMapper;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.http.*;
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

    public TypeSystemClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public List<AllTypeSystemsResponse> getTypeSystems(RequestContext requestContext) {
        return executeGet(
                requestContext,
                TYPE_SYSTEMS,
                TypeSystemsMapper::fromJsonString);
    }

    // Has to be done with executeMethod because otherwise causes problems with the trial version of CPI
    public String getTypeSystemMessagesRaw(RequestContext requestContext, String typeSystem) {
        return executeMethod(requestContext, PATH_FOR_TOKEN, format(TYPE_MESSAGE_VERSIONS_FORMAT, typeSystem),
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

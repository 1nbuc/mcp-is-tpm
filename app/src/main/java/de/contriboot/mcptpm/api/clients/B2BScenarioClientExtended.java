package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.b2bscenario.B2BScenarioClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.api.entities.B2BScenarioEntity;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

public class B2BScenarioClientExtended extends B2BScenarioClient {
    public B2BScenarioClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public String getB2BScenariosForAgreementRaw(RequestContext requestContext, TpmObjectMetadata agreementMetadata) {
        return executeGet(
                requestContext,
                format(B2B_SCENARIOS_RESOURCE, agreementMetadata.getObjectId()),
                (response) -> response
        );
    }

    @Bean
    public MethodToolCallback updateB2BScenario(RequestContext requestContext, B2BScenarioEntity b2bScenarioEntity) {
        executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                format(B2B_SCENARIOS_RESOURCE, b2bScenarioEntity.getParentId()),
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<B2BScenarioEntity> requestEntity = new HttpEntity<>(b2bScenarioEntity, httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url, HttpMethod.PUT, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't update b2bscenario. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody())
                        );
                    }


                    return "success";
                });

        return MethodToolCallback.builder().toolDefinition(ToolDefinition.builder().name("update-b2b-scenario").build()).build();
    }

    public String triggerB2BScenarioUpdate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

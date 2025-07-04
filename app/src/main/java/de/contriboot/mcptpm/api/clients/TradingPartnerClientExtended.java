package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.exception.ClientIntegrationException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.trading.TradingPartnerClient;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;
import de.contriboot.mcptpm.api.entities.mapper.SystemRequestMapper;
import org.springframework.http.*;

import java.util.List;

import static com.figaf.integration.tpm.utils.TpmUtils.PATH_FOR_TOKEN;
import static java.lang.String.format;

public class TradingPartnerClientExtended extends TradingPartnerClient {
    public static final String SECURITY_CONFIG_ACTIVATE_FORMAT = "/itspaces/tpm/tradingpartners/%s/config.signval?deploy=%s";

    public TradingPartnerClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public List<PartnerSystemEntity> getSystemsOfPartner(RequestContext requestContext, String partnerId) {
        return executeGet(
                requestContext,
                format(SYSTEMS_RESOURCE, partnerId),
                SystemRequestMapper::fromJsonArraListString
        );
    }

    public String activateSignatureVerifyConfig(RequestContext requestContext, String partnerId, String as2partnerId) {
        return executeMethod(
                requestContext,
                PATH_FOR_TOKEN,
                format(SECURITY_CONFIG_ACTIVATE_FORMAT, partnerId, as2partnerId),
                (url, token, restTemplateWrapper) -> {
                    HttpHeaders httpHeaders = createHttpHeadersWithCSRFToken(token);
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity requestEntity = new HttpEntity<>(httpHeaders);
                    ResponseEntity<String> responseEntity = restTemplateWrapper.getRestTemplate().exchange(url,
                            HttpMethod.POST, requestEntity, String.class);
                    if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                        throw new ClientIntegrationException(format(
                                "Couldn't create MAG. Code: %d, Message: %s",
                                responseEntity.getStatusCode().value(),
                                requestEntity.getBody()));
                    }

                    return responseEntity.getBody();
                });
    }

    public String getIdentifiers(RequestContext requestContext, String partnerId) {
        return executeGet(requestContext, format(IDENTIFIERS_RESOURCE, partnerId), (response) -> response);
    }
}

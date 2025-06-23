package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.trading.TradingPartnerClient;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;
import de.contriboot.mcptpm.api.entities.mapper.SystemRequestMapper;

import java.util.List;

import static java.lang.String.format;

public class TradingPartnerClientExtended  extends TradingPartnerClient {
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
}

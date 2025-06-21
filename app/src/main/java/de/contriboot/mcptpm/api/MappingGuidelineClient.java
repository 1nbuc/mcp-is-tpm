package de.contriboot.mcptpm.api;

import com.figaf.integration.common.client.BaseClient;
import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.factory.HttpClientsFactory;

public class MappingGuidelineClient extends BaseClient {

    public MappingGuidelineClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public String createMappingGuideline(RequestContext requestContext, String sourceMig, String targetMig, String name) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String updateMappingGuideline() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

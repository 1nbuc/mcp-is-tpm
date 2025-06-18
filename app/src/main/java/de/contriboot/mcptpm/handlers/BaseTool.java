package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import lombok.Getter;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;

import java.util.List;

@Getter
public abstract class BaseHandler {
    private RequestContext requestContext;
    private AgreementClient agreementClient;

    public BaseHandler(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    abstract List<SyncToolSpecification> getAllTools();
}

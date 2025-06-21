package de.contriboot.mcptpm.api;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.mig.MessageImplementationGuidelinesClient;

public class MigClientExtended extends MessageImplementationGuidelinesClient {
    public MigClientExtended(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }
}

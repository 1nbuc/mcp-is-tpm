package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.TpmBaseClient;
import de.contriboot.mcptpm.api.entities.deploy.CreateDeployRequest;
import de.contriboot.mcptpm.api.entities.deploy.GetDeployStatusEntity;

public class DeployClient extends TpmBaseClient {

    public DeployClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }


}

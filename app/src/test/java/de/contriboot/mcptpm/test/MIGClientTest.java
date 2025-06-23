package de.contriboot.mcptpm.test;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.factory.HttpClientsFactory;
import de.contriboot.mcptpm.api.clients.MigClientExtended;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import de.contriboot.mcptpm.utils.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class MIGClientTest {

    private String migId = "1551a01bb08543c7aa3d2b1a7939565e";
    private String migVersion = "a39e3b52573b42c19199cde3c0585718";
    private MigClientExtended client;
    private RequestContext requestContext;

    @BeforeEach
    void setUp() {

        client = new MigClientExtended(new HttpClientsFactory());
        requestContext = Config.getRequestContextFromEnv();
    }

    @Test
    void testGetMinimalNodeList() {
        MIGEntity migObj = client.getMigVersionRawObject(requestContext, migVersion);
        List<MigClientExtended.MinimalNode> allNodesList = client.getMinimalNodeList(migObj);
    }
}

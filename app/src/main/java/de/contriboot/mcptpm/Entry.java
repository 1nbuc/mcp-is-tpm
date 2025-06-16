package de.contriboot.mcptpm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

public class Entry {
    public static void main(String[] args) {
        StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(new ObjectMapper());
        McpSyncServer syncServer = McpServer.sync(transportProvider)
                .serverInfo("TPM-integration-suite", "1.0.0")
                .capabilities(
                        McpSchema.ServerCapabilities.builder()
                                .tools(true)
                                .build())
                .build();
    }

    public void registerAllTools(McpSyncServer server) {

    }
}
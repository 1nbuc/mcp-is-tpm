DISCLAIMER: This server is still experimental. Use at your own risk!

# MCP Trading Partner Management

A ModelContextProtocol (MCP) Server for SAP Integration Suite Trading Partner Management
I highly recommend using this Server along with https://github.com/1nbuc/mcp-integration-suite which provides tools for general CPI Functions

## Requirements
Java and Gradle (Java > 17, Gradle > 8)

## Installation
```sh
git clone https://github.com/1nbuc/mcp-is-tpm.git
cd mcp-is-tpm
./gradlew build
```

Add this to your AI Clients MCP Config.
This code snippets shows how to use it together with the MCP Integration suite server, which is optional.

If your S or P user is associated with a Universal ID, you have to provide details as below.
Please also use your Universal ID Password.

If you have a standalone S-User, delete the CPI_UNIVERSAL_MAIL variable
```json
{
  "mcpServers": {
    "mcp-is": {
      "command": "node",
      "args": [
        "<project path>/dist/index.js"
      ],
      "autoApprove": []
    },
    "mcp-is-tpm": {
      "command": "java",
      "args": [
        "-Dlogging.pattern.console=", 
        "-Dspring.main.web-application-type=none",
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "/Users/1nbuc/devshit/figaf/mcp-is-tpm/app/build/libs/app-0.0.1-SNAPSHOT.jar"
      ],
      "autoApprove": [],
      "env": {
        "CPI_URL": "https://https://your.cpi.url.hana.ondemand.com",
        "CPI_USER": "S001234567",
        "CPI_PASSWORD": "myPass123",
        "CPI_UNIVERSAL_MAIL": "mail@company.de"
      }
    }
  }
}
```

## Custom prompt
I highly recommend starting each chat with a custom prompt. You can find a recommended custom prompt [here](https://github.com/1nbuc/mcp-integration-suite?tab=readme-ov-file#custom-prompt)

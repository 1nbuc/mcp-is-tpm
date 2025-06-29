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
        "<project path of mcp-integration-suite>/dist/index.js"
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
        "<project path of mcp-is-tpm>/app/build/libs/app-0.0.1-SNAPSHOT.jar"
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

## Available Tools
*   `get-partner-metadata`: Get metadata for all trading partners.
*   `get-partner`: Get partner details by partner id.
*   `create-trading-partner`: Create a new trading partner.
*   `get-systems-of-partner`: Returns all systems of a trading partner by its ID.
*   `create-system`: Create a system for a trading partner.
*   `get-system-types`: Get available system types.
*   `create-identifier`: Create a partner Identifier.
*   `get-qualifiers-codelist`: Get codelist of a qualifier.
*   `create-communication`: Create a communication channel for a system of a trading partner.
*   `get-sender-adapters`: Get all sender adapters of trading partner systems.
*   `get-receiver-adapters`: Get all receiver adapters of trading partner systems.
*   `create-signature-verify-config`: Create Signature Verification configuration for a partner.
*   `activate-signature-verify-config`: Activate Signature Verification configuration for a partner.
*   `get-all-company-profile-metadata`: Get metadata for all company profiles.

### Agreement Management
*   `get-all-agreement-metadata`: Get metadata for all agreements.
*   `get-all-agreement-template-metadata`: Get metadata for all agreement templates.
*   `get-agreement-template`: Get all details for an agreement template.
*   `create-agreement-with-bound-template`: Create a new B2B agreement which is bound to a template.
*   `get-agreement-b2b-scenario`: Get the technical B2B scenario of an agreement.
*   `update-b2b-scenario`: Update an Agreement's B2B Scenario.
*   `trigger-agreement-activate-or-update-deployment`: Update or deploy an agreement.

### Message Implementation Guideline (MIG) Management
*   `get-all-mig-latest-metadata`: Get the latest metadata for all Message Implementation Guidelines (MIGs).
*   `get-mig-raw-by-id`: Get raw MIG content by its version ID.
*   `get-mig-nodes-xpath`: Get the Nodes of a MIG for a specified XPath.
*   `get-all-mig-fields`: Get a List of all fields of a MIG.
*   `get-mig-documentation-entry`: Get the documentation text for a id of a documentation within a mig.
*   `get-mig-proposal`: Get Proposal for a MIG.
*   `apply-mig-proposal`: Select fields based on MIG proposal.
*   `create-mig-draft-all-segments-selected`: Creates a draft MIG from a source version, with all segments and fields pre-selected.
*   `create-mig`: Create Message implementation guideline based on a type.
*   `change-mig-field-selection`: Change the selection of MIG fields.

### Mapping Guideline (MAG) Management
*   `get-all-mags-metadata`: Get an overview of available Mapping guidelines.
*   `create-mapping-guidelines`: Create a new mapping guidelines.
*   `test-mag-with-message`: Send a message against a mapping guideline and get the result.

### Monitoring
*   `search-interchanges`: Search for interchanges/TPM message monitoring based on filter criteria.
*   `get-interchange-payloads`: Get payload data list for a specific interchange.
*   `download-interchange-payload`: Download a specific payload by its ID.
*   `get-interchange-last-error`: Get last error details for a specific message/business document.

### Other
*   `get-type-systems`: Get available type systems.
*   `get-type-system-messages`: Get messages of a type system.
*   `get-type-system-message-full`: Get a message from a type system with all details including versions and revisions.
*   `create-custom-message`: Create a custom message in typesystem Customer_TS based on XSD.
*   `get-type-system-identifier-schemes`: Get the possible scheme for identifiers in a type system.
*   `get-all-business-process-roles`: Get all business process roles.
*   `get-all-business-processes`: Get all business processes.
*   `get-all-industry-classifications`: Get all industry classifications.
*   `get-all-product-classifications`: Get all product classifications.
*   `get-all-products`: Get all available products/types for a system e.g. SAP SuccessFactors etc.
*   `get-all-contries-or-regions`: Get all countries or regions.


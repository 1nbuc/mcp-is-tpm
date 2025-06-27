package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.figaf.integration.common.factory.HttpClientsFactory;
import de.contriboot.mcptpm.api.clients.MappingGuidelineClient;
import de.contriboot.mcptpm.api.clients.MigClientExtended;
import de.contriboot.mcptpm.api.clients.TypeSystemClient;
import de.contriboot.mcptpm.api.entities.mag.MAGEntity;
import de.contriboot.mcptpm.api.entities.mag.MAGProposalRequest;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MappingGuidelinesTools {
    MappingGuidelineClient client;
    MigClientExtended migClient;
    TypeSystemClient typeSystemClient;

    public MappingGuidelinesTools() {
        this.client = new MappingGuidelineClient(new HttpClientsFactory());
        this.migClient = new MigClientExtended(new HttpClientsFactory());
        this.typeSystemClient = new TypeSystemClient(new HttpClientsFactory());
    }

    @Tool(name = "create-mapping-guidelines", description = "Create a new mapping guidelines")
    public String createMappingGuidelines(
            String sourceTypeSystemId,
            String sourceMigVersionId,
            String targetTypeSystemId,
            String targetMigVersionId,
            String name,
            List<MIGEntity.BusinessContext> sourceBusinessContext,
            List<MIGEntity.BusinessContext> targetBusinessContext
    ) {
        return client.createMappingGuideline(
                Config.getRequestContextFromEnv(),
                sourceTypeSystemId,
                sourceMigVersionId,
                targetTypeSystemId,
                targetMigVersionId,
                name,
                sourceBusinessContext,
                targetBusinessContext
        );
    }

    @Tool(name = "test-mag-with-message", description = "send a message agains a mapping guideline and get the result")
    public String testMAGWithMessage(String magVersionId, String payload) {
        return client.simulatePayloadAgainstMAG(Config.getRequestContextFromEnv(), magVersionId, payload);
    }

    @Tool(name = "get-all-mags-metadata", description = "Get an overview of available Mapping guidelines")
    public JsonNode getAllMagsMetadata() {
        return ToolUtils.parseJson(client.getMAGMetadata(Config.getRequestContextFromEnv()));
    };

    // Not implemented for now. Maybe in the future. the application is way too complex and can be done with two clicks in browser
//    @Tool(name = "apply-mag-proposal", description = "Apply fieldmapping based on MAG proposal. E.g. Map all fields " +
//            "like the proposal says with a confidence score over 50%")
//    public JsonNode applyMAGProposal(
//            String magVersionId,
//            @ToolParam(description = "Minimum Confidence value for proposal in %. 0-100") int confidenceScore
//    ) {
//        MAGEntity magEntity = client.getMappingGuidelineRawObject(Config.getRequestContextFromEnv(), magVersionId);
//        MIGEntity targetMig = migClient.getMigVersionRawObject(
//                Config.getRequestContextFromEnv(),
//                magEntity.getTargetMig().getObjectGUID()
//        );
//        MIGEntity sourceMig = migClient.getMigVersionRawObject(
//                Config.getRequestContextFromEnv(),
//                magEntity.getSourceMig().getObjectGUID()
//        );
//
//        List<MAGProposalRequest.DomainGuid> targetMAGGuids = targetMig.getDomainGuidsWithCodeValues();
//        List<MAGProposalRequest.DomainGuid> sourceMAGGuids = sourceMig.getDomainGuidsWithCodeValues();
//
//
//        MAGProposalRequest proposalRequest = magEntity.getMAGProposalRequest(targetMig, sourceMig, targetMAGGuids, sourceMAGGuids);
//
//        return ToolUtils.parseJson(client.getMAGProposal(Config.getRequestContextFromEnv(), proposalRequest));
//
//    }
}

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

    @Tool(name = "apply-mag-proposal", description = "Apply fieldmapping based on MAG proposal. E.g. Map all fields " +
            "like the proposal says with a confidence score over 50%")
    public JsonNode applyMAGProposal(
            String magVersionId,
            @ToolParam(description = "Minimum Confidence value for proposal in %. 0-100") int confidenceScore
    ) {
        MAGEntity magEntity = client.getMappingGuidelineRawObject(Config.getRequestContextFromEnv(), magVersionId);
        MIGEntity targetMig = migClient.getMigVersionRawObject(
                Config.getRequestContextFromEnv(),
                magEntity.getTargetMig().getObjectGUID()
        );
        MIGEntity sourceMig = migClient.getMigVersionRawObject(
                Config.getRequestContextFromEnv(),
                magEntity.getSourceMig().getObjectGUID()
        );

        Map<String, MIGEntity.CodelistReferenceDetail> targetMigDomainGuids = targetMig.getAllDomainGuids(true);
        Map<String, MIGEntity.CodelistReferenceDetail> sourceMigDomainGuids = sourceMig.getAllDomainGuids(true);

        List<MAGProposalRequest.DomainGuid> targetMAGGuids = new ArrayList<>();
        List<MAGProposalRequest.DomainGuid> sourceMAGGuids = new ArrayList<>();

        for (var targetEntry : targetMigDomainGuids.entrySet()) {

            MAGProposalRequest.DomainGuid domainGuid = new MAGProposalRequest.DomainGuid();
            domainGuid.setDomainGuid(targetEntry.getKey());
            targetMAGGuids.add(domainGuid);

            if (targetEntry.getValue().isHasCodelistReference() == false) {
                continue;
            }

            List<String> vertexIds = typeSystemClient.getCodeValueVertexIds(
                    Config.getRequestContextFromEnv(),
                    targetMig.getMessageTemplate().getTypeSystemId(),
                    targetMig.getMessageTemplate().getVersionId(),
                    targetEntry.getValue().getCodelistReferenceId(),
                    targetEntry.getValue().getSelectedValues(),
                    targetEntry.getValue().isAllValuesSelected()
            );

            for (String vertexId : vertexIds) {
                MAGProposalRequest.DomainGuid domainGuidWithCodeValue = new MAGProposalRequest.DomainGuid();
                domainGuidWithCodeValue.setDomainGuid(targetEntry.getKey());
                domainGuidWithCodeValue.setCodeValueGuid(vertexId);
                targetMAGGuids.add(domainGuidWithCodeValue);
            }
        }

        for (var sourceEntry : sourceMigDomainGuids.entrySet()) {
            MAGProposalRequest.DomainGuid domainGuid = new MAGProposalRequest.DomainGuid();
            domainGuid.setDomainGuid(sourceEntry.getKey());
            sourceMAGGuids.add(domainGuid);

            if (!sourceEntry.getValue().isHasCodelistReference()) {
                continue;
            }

            List<String> vertexIds = typeSystemClient.getCodeValueVertexIds(
                    Config.getRequestContextFromEnv(),
                    sourceMig.getMessageTemplate().getTypeSystemId(),
                    sourceMig.getMessageTemplate().getVersionId(),
                    sourceEntry.getValue().getCodelistReferenceId(),
                    sourceEntry.getValue().getSelectedValues(),
                    sourceEntry.getValue().isAllValuesSelected()
            );

            for (String vertexId : vertexIds) {
                MAGProposalRequest.DomainGuid domainGuidWithCodeValue = new MAGProposalRequest.DomainGuid();
                domainGuidWithCodeValue.setDomainGuid(sourceEntry.getKey());
                domainGuidWithCodeValue.setCodeValueGuid(vertexId);
                sourceMAGGuids.add(domainGuidWithCodeValue);
            }
        }


        MAGProposalRequest proposalRequest = magEntity.getMAGProposalRequest(targetMig, sourceMig, targetMAGGuids, sourceMAGGuids);

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = objectWriter.writeValueAsString(proposalRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ToolUtils.parseJson(client.getMAGProposal(Config.getRequestContextFromEnv(), proposalRequest));

    }
}

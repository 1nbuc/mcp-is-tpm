package de.contriboot.mcptpm.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import de.contriboot.mcptpm.api.clients.MigClientExtended;
import de.contriboot.mcptpm.api.entities.mapper.MIGProposalResponseMapper;
import de.contriboot.mcptpm.api.entities.mig.MIGProposalResponse;
import de.contriboot.mcptpm.utils.JsonUtils;
import de.contriboot.mcptpm.utils.ToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.mig.MessageImplementationGuidelinesClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import com.figaf.integration.tpm.entity.mig.DraftCreationResponse;

import de.contriboot.mcptpm.api.entities.mig.CreateMIGRequest;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import de.contriboot.mcptpm.utils.Config;

@Slf4j
@Service
public class MessageImplementationGuidelinesTools {

    private MigClientExtended client;

    public MessageImplementationGuidelinesTools() {
        this.client = new MigClientExtended(new HttpClientsFactory());
    }

    @Tool(name = "get-all-mig-latest-metadata", description = "Get latest metadata for all Message Implementation Guidelines (MIGs)." +
            " If there is only one param for migId it is most of the time the migVersion ID which has to be provided")
    public List<TpmObjectMetadata> getAllMigLatestMetadata() {
        return client.getAllLatestMetadata(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-mig-raw-by-id", description = "Get raw MIG content by its version ID. " +
            "Will probably exceed context window, use only for small MIGs. Try to avoid this tool!")
    public String getMigRawById(@ToolParam(description = "ID of the MIG") String migVersionId) {
        return client.getRawById(migVersionId, Config.getRequestContextFromEnv());
    }

//    @Tool(name = "save-mig-all-segments-and-fields", description = "Saves a MIG, ensuring all segments and fields are selected. (Note: This operation might be subject to change based on client API evolution).")
//    public String saveMigAllSegmentsAndFields(@ToolParam(description = "ID of the MIG") String migVersionId, @ToolParam(description = "Version ID of the MIG to save") String migVersionId) {
//        client.saveAllSegmentsAndFields(Config.getRequestContextFromEnv(), migVersionId);
//        return "Successfully saved MIG with all segments and fields selected for version ID: " + migVersionId;
//    }


    @Tool(name = "create-mig-draft-all-segments-selected", description = "Creates a draft MIG from a source version, with all segments and fields pre-selected. (Note: This operation might be subject to change based on client API evolution).")
    public DraftCreationResponse createMigDraftWithAllSegmentsAndFieldsSelected(
            @ToolParam(description = "Name for the new draft MIG") String name,
            @ToolParam(description = "Version ID of the source MIG") String sourceMigVersionId) {
        return client.createDraftWithAllSegmentsAndFieldsSelected(Config.getRequestContextFromEnv(), name, sourceMigVersionId);
    }

    @Tool(name = "get-mig-nodes-xpath", description="Get the Nodes of a MIG for a specified XPath")
    public MIGEntity.Node getMigNodesXpath(
        String migVersionId,
        @ToolParam(description = "XPath of the message. Returns root element if empty", required = false) String xpath) {
            return new MIGEntity.Node();
    }

    @Tool(name = "get-documentations-for-notes", description = "Return the documentation of an element")
    public Map<String, MIGEntity.Documentation> getNodesDocumentation(
        String migVersionId,
        @ToolParam(description="List of GUIDs of the nodes you want documentation for") List<String> nodeGuidList,
        @ToolParam(description = "Weather to include Sub-Nodes or not. Default: false", required = false) boolean includeSubNodes) {

        MIGEntity entity = client.getMigVersionRawObject(Config.getRequestContextFromEnv(), migVersionId);

        throw new UnsupportedOperationException("Not yet implemented");
    }

    //TODO: be aware of local codelists
    @Tool(name = "get-qualifiers-codelist", description = "Get codelist of a qualifier")
    public List<String> getQualifierCodelist(String typesystem, String version, String qualifier) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Tool(name = "get-all-mig-fields", description = "Get a List of all fields of MIG")
    public List<MIGEntity.MinimalNode> getAllMigFields(
            String migVersionId,
            @ToolParam(description = "If true, only selected fields will be returned", required = true) boolean onlySelectedFields) {
        MIGEntity entity = client.getMigVersionRawObject(Config.getRequestContextFromEnv(), migVersionId);
        return entity.getMinimalNodeList(onlySelectedFields);
    }

    @Tool(name = "create-mig", description = "Create Message implementation guideline based on a type. " +
            "Check available type systems and type system messages first. " +
            "If souccessful it will return two IDs a migguid and a field called just id. You probably want to use the " +
            "id field since it corresponds to the migguidversion which is used by most tools")
    public JsonNode createMig(
        String name,
        String summary,
        String messageTemplateId,
        String messageTemplateTag,
        String typeSystemAcronym,
        String typeSystemId,
        @ToolParam(description = "Message version ID. Please fetch from get-type-system-message-full for a list. " +
                "If not further specified the user probably wants the latest version")
        String versionId,
        String messageTemplateName,
        @ToolParam(description="In, Out, Both") String direction, // In, Out, Both
        @ToolParam(description="Has to be at least 1. " +
                "Can be Business process, business process role, industry classification, product classification or geopolitical region" +
                "Please check the corresponding tools. " +
                "Try to specify as good as possible to get best MIG proposal results")
        ArrayList<CreateMIGRequest.OwnBusinessContext> ownBusinessContext,

        @ToolParam(description="Can be empty array" +
                "Can be Business process, business process role, industry classification, product classification or geopolitical region" +
                "Please check the corresponding tools. " +
                "Try to specify as good as possible to get best MIG proposal results")
        ArrayList<CreateMIGRequest.OwnBusinessContext> partnerBusinessContext
    ) {
        CreateMIGRequest migRequest = client.buildMIGCreateRequest(
            name, 
            summary, 
            messageTemplateId, 
            messageTemplateTag, 
            typeSystemAcronym, 
            typeSystemId, 
            versionId, 
            messageTemplateName, 
            direction, 
            ownBusinessContext
        );

        if (ownBusinessContext.isEmpty()) {
            throw new IllegalArgumentException("ownBusinessContext must have at least 1 element");
        }

        return ToolUtils.parseJson(client.createMIG(Config.getRequestContextFromEnv(), migRequest));
    }


    @Tool(name = "change-mig-field-selection", description = "Change the selection of MIG fields")
    public String changeMigFieldSelection(
            @ToolParam(description = "List of VertexGUIDs of the nodes you want to select")
            List<String> addToSelection,
            @ToolParam(description = "List of VertexGUIDs of the nodes you want to remove")
            List<String> removeSelection,
            String migVersionId
    ) {
        return client.changeNodeSelection(Config.getRequestContextFromEnv(), addToSelection, removeSelection, migVersionId);
    }

    @Tool(name = "get-mig-proposal", description = "Get Proposal for a MIG. Proposal is the recommendation in % on weather" +
            "a field should be selected or not. ConfidenceValue 1 means 100% confidence")
    public JsonNode getMigProposal(String migVersionId) {
        String migResponse = client.getMIGProposal(Config.getRequestContextFromEnv(), migVersionId);
        if (migResponse.length() > 999999) {

            return ToolUtils.parseJson("MIG response too big to show. If you want to make changes based on proposal try using apply-mig-proposal");
        }

        return ToolUtils.parseJson(migResponse);
    }

    @Tool(name = "apply-mig-proposal", description = "Select fields based on MIG proposal. E.g. select all fields which" +
            "have a score over 50%")
    public String applyMigProposal(String migVersionId, @ToolParam(description = "Minimum Confidence value for proposal in %. 0-100") int confidenceValue) {
        MIGEntity entity = client.getMigVersionRawObject(Config.getRequestContextFromEnv(), migVersionId);
        MIGProposalResponse proposalResponse = MIGProposalResponseMapper.fromJsonString(
                client.getMIGProposal(Config.getRequestContextFromEnv(), migVersionId)
        );

        entity.applyMIGProposalRequest(proposalResponse, (float) confidenceValue / 100);
        return client.updateMIG(Config.getRequestContextFromEnv(), migVersionId, entity);
    }
    
}

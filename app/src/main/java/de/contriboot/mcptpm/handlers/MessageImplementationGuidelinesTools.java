package de.contriboot.mcptpm.handlers;

import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.mig.MessageImplementationGuidelinesClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import com.figaf.integration.tpm.entity.mig.DraftCreationResponse;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageImplementationGuidelinesTools {

    private MessageImplementationGuidelinesClient client;

    public MessageImplementationGuidelinesTools() {
        this.client = new MessageImplementationGuidelinesClient(new HttpClientsFactory());
    }

    @Tool(name = "get-all-mig-latest-metadata", description = "Get latest metadata for all Message Implementation Guidelines (MIGs).")
    public List<TpmObjectMetadata> getAllMigLatestMetadata() {
        return client.getAllLatestMetadata(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-mig-raw-by-id", description = "Get raw MIG content by its version ID.")
    public String getMigRawById(@ToolParam(description = "Version ID of the MIG") String migVersionId) {
        return client.getRawById(migVersionId, Config.getRequestContextFromEnv());
    }

    @Tool(name = "save-mig-all-segments-and-fields", description = "Saves a MIG, ensuring all segments and fields are selected. (Note: This operation might be subject to change based on client API evolution).")
    public String saveMigAllSegmentsAndFields(@ToolParam(description = "Version ID of the MIG to save") String migVersionId) {
        client.saveAllSegmentsAndFields(Config.getRequestContextFromEnv(), migVersionId);
        return "Successfully saved MIG with all segments and fields selected for version ID: " + migVersionId;
    }

    @Tool(name = "delete-draft-mig", description = "Delete a draft MIG.")
    public String deleteDraftMig(
            @ToolParam(description = "Name of the MIG") String name,
            @ToolParam(description = "Version ID of the draft MIG to delete") String migVersionId) {
        client.deleteDraftMig(Config.getRequestContextFromEnv(), name, migVersionId);
        return "Successfully deleted draft MIG '" + name + "' with version ID: " + migVersionId;
    }

    @Tool(name = "create-mig-draft-all-segments-selected", description = "Creates a draft MIG from a source version, with all segments and fields pre-selected. (Note: This operation might be subject to change based on client API evolution).")
    public DraftCreationResponse createMigDraftWithAllSegmentsAndFieldsSelected(
            @ToolParam(description = "Name for the new draft MIG") String name,
            @ToolParam(description = "Version ID of the source MIG") String sourceMigVersionId) {
        return client.createDraftWithAllSegmentsAndFieldsSelected(Config.getRequestContextFromEnv(), name, sourceMigVersionId);
    }
}

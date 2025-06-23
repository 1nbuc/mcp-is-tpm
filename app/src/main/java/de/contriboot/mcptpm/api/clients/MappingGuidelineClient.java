package de.contriboot.mcptpm.api.clients;

import com.figaf.integration.common.entity.RequestContext;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.TpmBaseClient;
import de.contriboot.mcptpm.api.entities.mag.MAGCreateEntity;

import java.util.UUID;

public class MappingGuidelineClient extends TpmBaseClient {

    public MappingGuidelineClient(HttpClientsFactory httpClientsFactory) {
        super(httpClientsFactory);
    }

    public String createMappingGuideline(RequestContext requestContext, String sourceMig, String targetMig, String name) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String updateMappingGuideline() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String createMappingGuidelineRaw(RequestContext requestContext, String sourceMigGUID, String targetMigGUID, String name) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public MAGCreateEntity createMappingGuidelineEntity(RequestContext requestContext, String sourceMigGUID, String targetMigGUID, String name) {
        MAGCreateEntity entity = new MAGCreateEntity();
        entity.setArtifactMetadata(new MAGCreateEntity.ArtifactMetadata("1.1", "MagDetail"));
        entity.setIdentification(new MAGCreateEntity.Identification("1.0", ""));
        entity.setStatus("Draft");

        String nameId = generateGUID();
        String summaryId = generateGUID();
        String definitionId = generateGUID();

        MAGCreateEntity.Documentation documentation = new MAGCreateEntity.Documentation();
        documentation.setName(new MAGCreateEntity.ArtifactValue(nameId));
        documentation.setSummary(new MAGCreateEntity.ArtifactValue(summaryId));
        documentation.setDefinition(new MAGCreateEntity.ArtifactValue(definitionId));
        entity.setDocumentation(documentation);

        // TODO: add missing fields
        return entity;

    }

    private String generateGUID() {
        UUID uuid = UUID.randomUUID();
        // Format it with the "UID-" prefix
        return "UID-" + uuid.toString();
    }
}

package de.contriboot.mcptpm.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.figaf.integration.tpm.entity.trading.CreateSystemRequest;
import de.contriboot.mcptpm.api.entities.shared.*;

import java.util.ArrayList;
import java.util.List;

public class PartnerSystemEntity extends CreateSystemRequest {
    @JsonProperty("Setting")
    boolean setting;

    @JsonProperty("DocumentSchemaVersion")
    String documentSchemaVersion;

    @JsonProperty("administrativeData")
    AdministrativeData administrativeData = new AdministrativeData();

    @JsonProperty("searchableAttributes")
    SearchableAttributes searchableAttributes = new SearchableAttributes();

    @JsonProperty("artifactStatus")
    String artifactStatus;

    @JsonProperty("artifactProperties")
    ArtifactProperties artifactProperties = new ArtifactProperties();

    @JsonProperty("relations")
    List<Relation> relations = new ArrayList<>();

    @JsonProperty("ArtifactRelations")
    ArtifactRelations artifactRelations = new ArtifactRelations();
    @JsonProperty("displayName")
    String displayName;
    @JsonProperty("semanticVersion")
    String semanticVersion;
    @JsonProperty("artifactType")
    String artifactType;
    @JsonProperty("id")
    String idLowercase;
    @JsonProperty("uniqueId")
    private String uniqueId;
}

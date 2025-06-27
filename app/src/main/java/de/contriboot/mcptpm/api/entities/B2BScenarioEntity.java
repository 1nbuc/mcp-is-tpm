package de.contriboot.mcptpm.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.contriboot.mcptpm.api.entities.shared.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class B2BScenarioEntity {

    @JsonProperty("IsVanScenario")
    private boolean isVanScenario;

    @JsonProperty("FunctionalAck")
    private FunctionalAck functionalAck = new FunctionalAck();

    @JsonProperty("BusinessTransactions")
    private List<BusinessTransaction> businessTransactions = new ArrayList<>();

    @JsonProperty("DocumentSchemaVersion")
    private String documentSchemaVersion;

    @JsonProperty("administrativeData")
    private AdministrativeData administrativeData = new AdministrativeData();

    @JsonProperty("ParentId")
    private String parentId;

    @JsonProperty("searchableAttributes")
    private SearchableAttributes searchableAttributes = new SearchableAttributes();

    @JsonProperty("artifactProperties")
    private ArtifactProperties artifactProperties = new ArtifactProperties();

    @JsonProperty("relations")
    private List<Relation> relations = new ArrayList<>();

    @JsonProperty("ArtifactRelations")
    private ArtifactRelations artifactRelations = new ArtifactRelations();

    @JsonProperty("artifactStatus")
    private String artifactStatus;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("semanticVersion")
    private String semanticVersion;

    @JsonProperty("id")
    private String id;

    @JsonProperty("artifactType")
    private String artifactType;


}


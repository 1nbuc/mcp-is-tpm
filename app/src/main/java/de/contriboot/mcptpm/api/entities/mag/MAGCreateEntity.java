package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class MAGCreateEntity {

    @JsonProperty("ArtifactMetadata")
    private ArtifactMetadata artifactMetadata = new ArtifactMetadata();

    @JsonProperty("Identification")
    private Identification identification = new Identification();

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Documentation")
    private Documentation documentation = new Documentation();

    @JsonProperty("SourceMig")
    private SourceMig sourceMig = new SourceMig();

    @JsonProperty("TargetMig")
    private TargetMig targetMig = new TargetMig();

    @JsonProperty("DocumentationArtifacts")
    private Map<String, String> documentationArtifacts;

    @JsonProperty("SourceBusinessContext")
    private List<MIGEntity.BusinessContext> sourceBusinessContext = new ArrayList<>();

    @JsonProperty("TargetBusinessContext")
    private List<MIGEntity.BusinessContext> targetBusinessContext = new ArrayList<>();

    @JsonProperty("DomainMappingElementsWithTransformation")
    private List<Object> domainMappingElementsWithTransformation = new ArrayList<>(); // Assuming empty array of objects

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        private String schemaVersion;

        @JsonProperty("ArtifactType")
        private String artifactType;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Identification {
        @JsonProperty("MAGVersion")
        private String magVersion;

        @JsonProperty("MAGGUID")
        private String magguid;
    }

    @Getter
    @Setter
    @ToString
    public static class Documentation {
        @JsonProperty("Name")
        private ArtifactValue name = new ArtifactValue();

        @JsonProperty("Summary")
        private ArtifactValue summary = new ArtifactValue();

        @JsonProperty("Definition")
        private ArtifactValue definition = new ArtifactValue();

        @JsonProperty("Notes")
        private List<Object> notes = new ArrayList<>(); // Assuming empty array of objects

        @JsonProperty("NumberOfNotes")
        private int numberOfNotes;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ArtifactValue {
        @JsonProperty("Id")
        private String id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SourceMig {
        @JsonProperty("ObjectGUID")
        private String objectGUID;

        @JsonProperty("TypeSystemId")
        private String typeSystemId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class TargetMig {
        @JsonProperty("ObjectGUID")
        private String objectGUID;

        @JsonProperty("TypeSystemId")
        private String typeSystemId;
    }

    @Getter
    @Setter
    @ToString
    public static class ContextValue {
        @JsonProperty("key")
        private String key;

        @JsonProperty("name")
        private String name;
    }
}

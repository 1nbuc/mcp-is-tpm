package de.contriboot.mcptpm.api.entities.mig;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class CreateMIGRequest {
    @JsonProperty("Identification")
    private Identification identification;
    @JsonProperty("Direction")
    private String direction;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Documentation")
    private Documentation documentation;
    @JsonProperty("MessageTemplate")
    private MessageTemplate messageTemplate;
    @JsonProperty("OwnBusinessContext")
    private ArrayList<OwnBusinessContext> ownBusinessContext;
    @JsonProperty("PartnerBusinessContext")
    private ArrayList<Object> partnerBusinessContext;
    @JsonProperty("DocumentationArtifacts")
    private Map<String, String> documentationArtifacts;

    @Getter
    @Setter
    public static class ArtifactValue {
        @JsonProperty("Id")
        private String id;
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("LanguageCode")
        private String languageCode;
    }

    @Getter
    @Setter
    public static class BaseArtifactValue {
        @JsonProperty("Id")
        private String id;
    }

    @Getter
    @Setter
    public static class ContextValue {
        private String key;
        private String name;
    }

    @Getter
    @Setter
    public static class Documentation {
        @JsonProperty("Name")
        private Name name;
        @JsonProperty("Summary")
        private Summary summary;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Identification {
        @JsonProperty("MIGVersion")
        private String mIGVersion;
    }

    @Getter
    @Setter
    public static class MessageTemplate {
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("IsCustomObject")
        private boolean isCustomObject;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("TypeSystemAcronym")
        private String typeSystemAcronym;
        @JsonProperty("TypeSystemId")
        private String typeSystemId;
        @JsonProperty("VersionId")
        private String versionId;
        @JsonProperty("Documentation")
        private Documentation documentation;
        @JsonProperty("DisplayTag")
        private String displayTag;
        @JsonProperty("VersionAcronym")
        private String versionAcronym;
    }

    @Getter
    @Setter
    public static class Name {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
        @JsonProperty("BaseArtifactValue")
        private BaseArtifactValue baseArtifactValue;
    }

    @Getter
    @Setter
    public static class OwnBusinessContext {
        private String contextType;
        private String name;
        private String codeListId;
        private ArrayList<ContextValue> contextValues;
    }

    @Getter
    @Setter
    public static class Summary {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

}
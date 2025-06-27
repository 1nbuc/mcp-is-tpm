package de.contriboot.mcptpm.api.entities.typeSystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class AllTypeSystemsResponse {
    @JsonProperty("ArtifactMetadata")
    public ArtifactMetadata artifactMetadata;
    @JsonProperty("Identification")
    public Identification identification;
    @JsonProperty("VertexGUID")
    public String vertexGUID;
    @JsonProperty("Acronym")
    public String acronym;
    @JsonProperty("Logo")
    public Logo logo;
    @JsonProperty("ResponsibleAgency")
    public ResponsibleAgency responsibleAgency;
    @JsonProperty("Website")
    public String website;
    @JsonProperty("ProvisionedBy")
    public ArrayList<String> provisionedBy;
    @JsonProperty("ProvisionType")
    public String provisionType;
    @JsonProperty("LicenseType")
    public String licenseType;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("Documentation")
    public Documentation documentation;
    @JsonProperty("NumberOfTypeSystemVersions")
    public int numberOfTypeSystemVersions;
    @JsonProperty("NumberOfMessages")
    public int numberOfMessages;
    @JsonProperty("NumberOfComplexTypes")
    public int numberOfComplexTypes;
    @JsonProperty("NumberOfSimpleTypes")
    public int numberOfSimpleTypes;
    @JsonProperty("NumberOfCodeLists")
    public int numberOfCodeLists;
    @JsonProperty("DocumentationArtifacts")
    public Map<String, String> documentationArtifacts;
    @JsonProperty("IsExternalCodelist")
    public boolean isExternalCodelist;
    @JsonProperty("IsSubset")
    public boolean isSubset;

    @Getter
    @Setter
    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        public String schemaVersion;
        @JsonProperty("ArtifactType")
        public String artifactType;
    }

    @Getter
    @Setter
    public static class BaseArtifactValue {
        @JsonProperty("VertexGUID")
        public String vertexGUID;
        @JsonProperty("Id")
        public String id;
        @JsonProperty("LanguageCode")
        public String languageCode;
        public String action;
    }

    @Getter
    @Setter
    public static class Copyright {
        @JsonProperty("BaseArtifactValue")
        public BaseArtifactValue baseArtifactValue;
    }

    @Getter
    @Setter
    public static class Definition {
        @JsonProperty("BaseArtifactValue")
        public BaseArtifactValue baseArtifactValue;
    }

    @Getter
    @Setter
    public static class Documentation {
        @JsonProperty("Name")
        public Name name;
        @JsonProperty("Definition")
        public Definition definition;
        @JsonProperty("Copyright")
        public Copyright copyright;
    }

    @Getter
    @Setter
    public static class Identification {
        @JsonProperty("Category")
        public String category;
        @JsonProperty("Id")
        public String id;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
        @JsonProperty("TypeSystemAcronym")
        public String typeSystemAcronym;
        @JsonProperty("ResponsibleAgencyCode")
        public String responsibleAgencyCode;
    }

    @Getter
    @Setter
    public static class Logo {
        @JsonProperty("MimeType")
        public String mimeType;
        @JsonProperty("CharacterSet")
        public String characterSet;
        @JsonProperty("Value")
        public String value;
        @JsonProperty("File")
        public String file;
    }

    @Getter
    @Setter
    public static class Name {
        @JsonProperty("BaseArtifactValue")
        public BaseArtifactValue baseArtifactValue;
    }

    @Getter
    @Setter
    public static class ResponsibleAgency {
        @JsonProperty("Code")
        public String code;
        @JsonProperty("Name")
        public String name;
        @JsonProperty("Definition")
        public String definition;
        @JsonProperty("Acronym")
        public String acronym;
    }


}


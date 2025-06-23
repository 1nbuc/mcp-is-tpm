package de.contriboot.mcptpm.api.entities.mig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MIGProposalResponse {
    @JsonProperty("ArtifactMetadata")
    public ArtifactMetadata artifactMetadata;
    @JsonProperty("Identification")
    public Identification identification;
    @JsonProperty("Metadata")
    public Metadata metadata;
    @JsonProperty("MigProposal")
    public ArrayList<MigProposal> migProposal;

    public static class AdditionalItems {
        @JsonProperty("PropertyName")
        public String propertyName;
        @JsonProperty("Id")
        public String id;
    }

    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        public String schemaVersion;
        @JsonProperty("ArtifactType")
        public String artifactType;
    }

    public static class Identification {
        @JsonProperty("ObjectGUID")
        public String objectGUID;
        @JsonProperty("MIGGUID")
        public String mIGGUID;
        @JsonProperty("MIGVersion")
        public String mIGVersion;
        @JsonProperty("Category")
        public String category;
        @JsonProperty("Customer")
        public String customer;
    }

    public static class Metadata {
        @JsonProperty("Status")
        public String status;
        @JsonProperty("Own")
        public int own;
        @JsonProperty("Foreign")
        public int foreign;
        @JsonProperty("Count")
        public int count;
        @JsonProperty("EnvelopeCount")
        public int envelopeCount;
        @JsonProperty("Message")
        public String message;
        @JsonProperty("RequestTime")
        public long requestTime;
    }

    @Getter
    @Setter
    public static class MigProposal {
        @JsonProperty("DomainGUID")
        public String domainGUID;
        @JsonProperty("VertexGUID")
        public String vertexGUID;
        @JsonProperty("Category")
        public String category;
        @JsonProperty("XPath")
        public String xPath;
        @JsonProperty("ConfidenceValue")
        public double confidenceValue;
        @JsonProperty("AdditionalItems")
        public AdditionalItems additionalItems;
    }


}


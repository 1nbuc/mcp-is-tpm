package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MAGProposalRequest {
    @JsonProperty("MagProposalRequestSchemaVersion")
    private String magProposalRequestSchemaVersion;
    @JsonProperty("Identification")
    public Identification identification;
    @JsonProperty("SourceBusinessContext")
    public List<MAGEntity.SourceBusinessContext> sourceBusinessContext;
    @JsonProperty("TargetBusinessContext")
    public List<MAGEntity.TargetBusinessContext> targetBusinessContext;
    @JsonProperty("SourceDomainGuids")
    public List<DomainGuid> sourceDomainGuids;
    @JsonProperty("TargetDomainGuids")
    public List<DomainGuid> targetDomainGuids;
    @JsonProperty("SourceMig")
    public MAGEntity.SourceMig sourceMig;
    @JsonProperty("TargetMig")
    public MAGEntity.TargetMig targetMig;
    @JsonProperty("SourceMessageTemplate")
    public MIGEntity.MessageTemplate sourceMessageTemplate;
    @JsonProperty("TargetMessageTemplate")
    public MIGEntity.MessageTemplate targetMessageTemplate;
    @JsonProperty("MagValidationRelevantInformation")
    public MagValidationRelevantInformation magValidationRelevantInformation;


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
    public static class ContextValue {
        public String key;
        public String name;
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
        @JsonProperty("NumberOfNotes")
        public int numberOfNotes;
        @JsonProperty("Notes")
        public ArrayList<Object> notes;
    }

    @Getter
    @Setter
    public static class Identification {
        @JsonProperty("ObjectGuid")
        public String objectGuid;
        @JsonProperty("MAGGUID")
        public String mAGGUID;
        @JsonProperty("ImportCorrelationGroupId")
        public String importCorrelationGroupId;
        @JsonProperty("ImportCorrelationObjectId")
        public String importCorrelationObjectId;
        @JsonProperty("MAGVersion")
        public String mAGVersion;
        @JsonProperty("Customer")
        public String customer;
        @JsonProperty("MagProposalRequestId")
        public long magProposalRequestId;
    }

    @Getter
    @Setter
    public static class MagValidationRelevantInformation {
        @JsonProperty("SharedVariables")
        public ArrayList<Object> sharedVariables;
    }

    @Getter
    @Setter
    public static class Name {
        @JsonProperty("BaseArtifactValue")
        public BaseArtifactValue baseArtifactValue;
    }


    @Getter
    @Setter
    public static class SourceBusinessContext {
        public String contextType;
        public String codeListId;
        public ArrayList<ContextValue> contextValues;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DomainGuid {
        @JsonProperty("DomainGuid")
        public String domainGuid;
        @JsonProperty("CodeValueGuid")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String codeValueGuid;

        public DomainGuid(String domainGuid) {
            this.domainGuid = domainGuid;
            this.codeValueGuid = null;
        }
    }

    @Getter
    @Setter
    public static class SourceMessageTemplate {
        @JsonProperty("Tag")
        public String tag;
        @JsonProperty("DisplayTag")
        public String displayTag;
        @JsonProperty("Documentation")
        public Documentation documentation;
        @JsonProperty("Id")
        public String id;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
        @JsonProperty("TypeSystemAcronym")
        public String typeSystemAcronym;
        @JsonProperty("VersionId")
        public String versionId;
        @JsonProperty("Revision")
        public int revision;
    }

    @Getter
    @Setter
    public static class SourceMig {
        @JsonProperty("ObjectGUID")
        public String objectGUID;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
    }

    @Getter
    @Setter
    public static class TargetBusinessContext {
        public String contextType;
        public String codeListId;
        public ArrayList<ContextValue> contextValues;
    }

    @Getter
    @Setter
    public static class TargetDomainGuid {
        @JsonProperty("DomainGuid")
        public String domainGuid;
        @JsonProperty("CodeValueGuid")
        public String codeValueGuid;
    }

    @Getter
    @Setter
    public static class TargetMessageTemplate {
        @JsonProperty("Tag")
        public String tag;
        @JsonProperty("DisplayTag")
        public String displayTag;
        @JsonProperty("Documentation")
        public Documentation documentation;
        @JsonProperty("Id")
        public String id;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
        @JsonProperty("TypeSystemAcronym")
        public String typeSystemAcronym;
        @JsonProperty("VersionId")
        public String versionId;
        @JsonProperty("Revision")
        public int revision;
    }

    @Getter
    @Setter
    public static class TargetMig {
        @JsonProperty("ObjectGUID")
        public String objectGUID;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
    }

}

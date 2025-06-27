package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.figaf.integration.common.factory.HttpClientsFactory;
import de.contriboot.mcptpm.api.clients.TypeSystemClient;
import de.contriboot.mcptpm.api.entities.mag.proposal.response.MAGProposalResponse;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MAGEntity {
    @JsonProperty("ArtifactMetadata")
    private ArtifactMetadata artifactMetadata;
    @JsonProperty("Identification")
    private Identification identification;
    @JsonProperty("SourceBusinessContext")
    private ArrayList<SourceBusinessContext> sourceBusinessContext;
    @JsonProperty("TargetBusinessContext")
    private ArrayList<TargetBusinessContext> targetBusinessContext;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("SourceMig")
    private SourceMig sourceMig;
    @JsonProperty("TargetMig")
    private TargetMig targetMig;
    @JsonProperty("DomainMappingElementsWithTransformation")
    private ArrayList<DomainMappingElementsWithTransformation> domainMappingElementsWithTransformation;
    @JsonProperty("AdministrativeData")
    private AdministrativeData administrativeData;
    @JsonProperty("Documentation")
    private Documentation documentation;
    @JsonProperty("DocumentationArtifacts")
    private Map<String, String> documentationArtifacts;
    @JsonProperty("TargetNodeStatus")
    private TargetNodeStatus targetNodeStatus;
    @JsonProperty("SharedFunctions")
    private ArrayList<SharedFunction> sharedFunctions;
    @JsonProperty("SharedVariables")
    private ArrayList<SharedVariable> sharedVariables;
    @JsonProperty("TargetNodeDuplicationRules")
    private ArrayList<Object> targetNodeDuplicationRules;

    @Getter
    @Setter
    public static class SharedVariable{
        @JsonProperty("VariableGuid")
        private String variableGuid;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Summary")
        private String summary;
        @JsonProperty("Default")
        private String defaultAttr;
        @JsonProperty("CreatedBy")
        private String createdBy;
        @JsonProperty("CreatedOn")
        private Date createdOn;
        @JsonProperty("ModifiedBy")
        private String modifiedBy;
        @JsonProperty("ModifiedOn")
        private Date modifiedOn;
    }

    @Getter
    @Setter
    public class SharedFunction{
        @JsonProperty("Name")
        private String name;
        @JsonProperty("DocumentationHtmlMode")
        private boolean documentationHtmlMode;
        @JsonProperty("Confidential")
        private boolean confidential;
        @JsonProperty("Origin")
        private String origin;
        @JsonProperty("CreatedBy")
        private String createdBy;
        @JsonProperty("CreatedOn")
        private Date createdOn;
        @JsonProperty("ModifiedBy")
        private String modifiedBy;
        @JsonProperty("ModifiedOn")
        private Date modifiedOn;
        @JsonProperty("IsCondition")
        private boolean isCondition;
        @JsonProperty("FunctionGuid")
        private String functionGuid;
        @JsonProperty("FunctionImplementations")
        private ArrayList<FunctionImplementation> functionImplementations;
        private boolean isReference;
    }

    @Setter
    @Getter
    public static class AdministrativeData {
        @JsonProperty("CreatedBy")
        private String createdBy;
        @JsonProperty("CreatedOn")
        private long createdOn;
        @JsonProperty("ModifiedBy")
        private String modifiedBy;
        @JsonProperty("ModifiedOn")
        private long modifiedOn;
        @JsonProperty("CreatedFrom")
        private String createdFrom;
    }

    @Setter
    @Getter
    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        private String schemaVersion;
        @JsonProperty("ArtifactType")
        private String artifactType;
    }

    @Setter
    @Getter
    public static class ArtifactValue {
        @JsonProperty("Id")
        private String id;
        private String action;
    }

    @Setter
    @Getter
    public static class ContextValue {
        private String key;
        private String name;
    }

    @Setter
    @Getter
    public static class Definition {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class Documentation {
        @JsonProperty("Notes")
        private ArrayList<Note> notes;
        @JsonProperty("NumberOfNotes")
        private int numberOfNotes;
        @JsonProperty("Name")
        private Name name;
        @JsonProperty("Definition")
        private Definition definition;
        @JsonProperty("Summary")
        private Summary summary;
    }

    @Getter
    @Setter
    public static class Note{
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
        @JsonProperty("Properties")
        private Properties properties;
    }

    @Getter
    @Setter
    public static class Category{
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
        @JsonProperty("PropertyName")
        private String propertyName;
    }

    @Getter
    @Setter
    public static class Properties{
        @JsonProperty("Category")
        private Category category;
        @JsonProperty("Number")
        private Number number;
    }


    @Setter
    @Getter
    public static class DomainMappingElement {
        @JsonProperty("SourceDomainSet")
        private ArrayList<SourceDomainSet> sourceDomainSet;
        @JsonProperty("TargetDomainSet")
        private ArrayList<TargetDomainSet> targetDomainSet;
        @JsonProperty("Documentation")
        private Documentation documentation;
    }

    @Setter
    @Getter
    public static class DomainMappingElementsWithTransformation {
        @JsonProperty("DomainMappingElement")
        private DomainMappingElement domainMappingElement;
        @JsonProperty("Transformation")
        private Transformation transformation;
        @JsonProperty("Confidence")
        private double confidence;
        @JsonProperty("Confidential")
        private boolean confidential;
    }

    @Setter
    @Getter
    public static class Function {
        @JsonProperty("FunctionGuid")
        private String functionGuid;
        @JsonProperty("isReference")
        private boolean isReference;
        @JsonProperty("FunctionParameters")
        private ArrayList<FunctionParameter> functionParameters;
        @JsonProperty("FunctionImplementations")
        private ArrayList<FunctionImplementation> functionImplementations;
    }

    @Setter
    @Getter
    public static class FunctionImplementation {
        @JsonProperty("SourceCode")
        private String sourceCode;
        @JsonProperty("FunctionImplementationLanguage")
        private FunctionImplementationLanguage functionImplementationLanguage;
    }

    @Setter
    @Getter
    public static class FunctionImplementationLanguage {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Version")
        private String version;
    }

    @Setter
    @Getter
    public static class FunctionParameter {
        @JsonProperty("PositionInParameterList")
        private int positionInParameterList;
        @JsonProperty("Direction")
        private String direction;
        @JsonProperty("Type")
        private String type;
        @JsonProperty("Name")
        private String name;
    }

    @Setter
    @Getter
    public static class Identification {
        @JsonProperty("ObjectGuid")
        private String objectGuid;
        @JsonProperty("MAGGUID")
        private String mAGGUID;
        @JsonProperty("ImportCorrelationGroupId")
        private String importCorrelationGroupId;
        @JsonProperty("ImportCorrelationObjectId")
        private String importCorrelationObjectId;
        @JsonProperty("MAGVersion")
        private String mAGVersion;
        @JsonProperty("Customer")
        private String customer;
    }

    @Setter
    @Getter
    public static class Name {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class ParameterAssignment {
        @JsonProperty("PositionInParameterList")
        private int positionInParameterList;
        @JsonProperty("PositionInParameter")
        private int positionInParameter;
        @JsonProperty("PositionInDomainSet")
        private int positionInDomainSet;
    }


    @Setter
    @Getter
    public static class SourceBusinessContext {
        private String contextType;
        private String codeListId;
        private ArrayList<ContextValue> contextValues;
    }

    @Setter
    @Getter
    public static class SourceDomainSet {
        @JsonProperty("DomainGuid")
        private String domainGuid;
        @JsonProperty("Position")
        private int position;
    }

    @Setter
    @Getter
    public static class SourceMig {
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("TypeSystemId")
        private String typeSystemId;
    }

    @Setter
    @Getter
    public static class Summary {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class TargetBusinessContext {
        private String contextType;
        private String codeListId;
        private ArrayList<ContextValue> contextValues;
    }

    @Setter
    @Getter
    public static class TargetDomainSet {
        @JsonProperty("DomainGuid")
        private String domainGuid;
        @JsonProperty("Position")
        private int position;
    }

    @Setter
    @Getter
    public static class TargetMig {
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("TypeSystemId")
        private String typeSystemId;
    }

    @Setter
    @Getter
    public static class TargetNodeStatus {
        // TODO: placeholder bc. of deserialization error
        private Object name;
    }

    @Setter
    @Getter
    public static class Transformation {
        @JsonProperty("Function")
        private Function function;
        @JsonProperty("ParameterAssignments")
        private ArrayList<ParameterAssignment> parameterAssignments;
    }

    public MAGProposalRequest getMAGProposalRequest(
            MIGEntity targetMigEntity,
            MIGEntity sourceMigEntity
    ) {
        MAGProposalRequest request = new MAGProposalRequest();
        MAGProposalRequest.Identification identification = new MAGProposalRequest.Identification();
        identification.setMAGGUID(getIdentification().getMAGGUID());
        identification.setCustomer(getIdentification().getCustomer());
        identification.setMAGVersion(getIdentification().getMAGVersion());
        identification.setMagProposalRequestId(System.currentTimeMillis());
        identification.setImportCorrelationObjectId(getIdentification().getImportCorrelationObjectId());
        identification.setImportCorrelationGroupId(getIdentification().getImportCorrelationGroupId());
        identification.setObjectGuid(getIdentification().getObjectGuid());
        request.setIdentification(identification);

        request.setMagProposalRequestSchemaVersion("1.0");

        request.setSourceBusinessContext(getSourceBusinessContext());
        request.setTargetBusinessContext(getTargetBusinessContext());

        request.setTargetDomainGuids(targetMigEntity.getDomainGuidsWithCodeValues());
        request.setSourceDomainGuids(sourceMigEntity.getDomainGuidsWithCodeValues());

        request.setSourceMig(getSourceMig());
        request.setTargetMig(getTargetMig());

        request.setSourceMessageTemplate(sourceMigEntity.getMessageTemplate());
        request.setTargetMessageTemplate(targetMigEntity.getMessageTemplate());

        MAGProposalRequest.MagValidationRelevantInformation magValidationRelevantInformation = new MAGProposalRequest.MagValidationRelevantInformation();
        magValidationRelevantInformation.setSharedVariables(new ArrayList<>());
        request.setMagValidationRelevantInformation(magValidationRelevantInformation);

        return request;
    }

    public void applyMAGProposal(MAGProposalResponse magProposalResponse) {


    }
}
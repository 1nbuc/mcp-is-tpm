package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

public class MAGResponse {
    @JsonProperty("ArtifactMetadata")
    public ArtifactMetadata artifactMetadata;
    @JsonProperty("Identification")
    public Identification identification;
    @JsonProperty("SourceBusinessContext")
    public ArrayList<SourceBusinessContext> sourceBusinessContext;
    @JsonProperty("TargetBusinessContext")
    public ArrayList<TargetBusinessContext> targetBusinessContext;
    @JsonProperty("Status")
    public String status;
    @JsonProperty("SourceMig")
    public SourceMig sourceMig;
    @JsonProperty("TargetMig")
    public TargetMig targetMig;
    @JsonProperty("DomainMappingElementsWithTransformation")
    public ArrayList<DomainMappingElementsWithTransformation> domainMappingElementsWithTransformation;
    @JsonProperty("AdministrativeData")
    public AdministrativeData administrativeData;
    @JsonProperty("Documentation")
    public Documentation documentation;
    @JsonProperty("DocumentationArtifacts")
    public DocumentationArtifacts documentationArtifacts;
    @JsonProperty("TargetNodeStatus")
    public TargetNodeStatus targetNodeStatus;
    @JsonProperty("SharedFunctions")
    public ArrayList<SharedFunction> sharedFunctions;
    @JsonProperty("SharedVariables")
    public ArrayList<SharedVariable> sharedVariables;

    public static class SharedVariable{
        @JsonProperty("VariableGuid")
        public String variableGuid;
        @JsonProperty("Name")
        public String name;
        @JsonProperty("Summary")
        public String summary;
        @JsonProperty("Default")
        public String defaultAttr;
        @JsonProperty("CreatedBy")
        public String createdBy;
        @JsonProperty("CreatedOn")
        public Date createdOn;
        @JsonProperty("ModifiedBy")
        public String modifiedBy;
        @JsonProperty("ModifiedOn")
        public Date modifiedOn;
    }

    @Getter
    @Setter
    public class SharedFunction{
        @JsonProperty("Name")
        public String name;
        @JsonProperty("DocumentationHtmlMode")
        public boolean documentationHtmlMode;
        @JsonProperty("Confidential")
        public boolean confidential;
        @JsonProperty("Origin")
        public String origin;
        @JsonProperty("CreatedBy")
        public String createdBy;
        @JsonProperty("CreatedOn")
        public Date createdOn;
        @JsonProperty("ModifiedBy")
        public String modifiedBy;
        @JsonProperty("ModifiedOn")
        public Date modifiedOn;
        @JsonProperty("IsCondition")
        public boolean isCondition;
        @JsonProperty("FunctionGuid")
        public String functionGuid;
        @JsonProperty("FunctionImplementations")
        public ArrayList<FunctionImplementation> functionImplementations;
        public boolean isReference;
    }

    @Setter
    @Getter
    public static class AdministrativeData {
        @JsonProperty("CreatedBy")
        public String createdBy;
        @JsonProperty("CreatedOn")
        public long createdOn;
        @JsonProperty("ModifiedBy")
        public String modifiedBy;
        @JsonProperty("ModifiedOn")
        public long modifiedOn;
    }

    @Setter
    @Getter
    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        public String schemaVersion;
        @JsonProperty("ArtifactType")
        public String artifactType;
    }

    @Setter
    @Getter
    public static class ArtifactValue {
        @JsonProperty("Id")
        public String id;
        public String action;
    }

    @Setter
    @Getter
    public static class ContextValue {
        public String key;
        public String name;
    }

    @Setter
    @Getter
    public static class Definition {
        @JsonProperty("ArtifactValue")
        public ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class Documentation {
        @JsonProperty("Notes")
        public ArrayList<Note> notes;
        @JsonProperty("NumberOfNotes")
        public int numberOfNotes;
        @JsonProperty("Name")
        public Name name;
        @JsonProperty("Definition")
        public Definition definition;
        @JsonProperty("Summary")
        public Summary summary;
    }

    @Getter
    @Setter
    public static class Note{
        @JsonProperty("ArtifactValue")
        public ArtifactValue artifactValue;
        @JsonProperty("Properties")
        public Properties properties;
    }

    @Getter
    @Setter
    public static class Category{
        @JsonProperty("ArtifactValue")
        public ArtifactValue artifactValue;
        @JsonProperty("PropertyName")
        public String propertyName;
    }

    @Getter
    @Setter
    public static class Properties{
        @JsonProperty("Category")
        public Category category;
        @JsonProperty("Number")
        public Number number;
    }

    @Setter
    @Getter
    public static class DocumentationArtifacts {
        @JsonProperty("84ef6e094a69391589ec6bdbba8dec9f")
        public String _84ef6e094a69391589ec6bdbba8dec9f;
        @JsonProperty("6666cd76f96936469e7be39d750cc7d9")
        public String _6666cd76f96936469e7be39d750cc7d9;
    }

    @Setter
    @Getter
    public static class DomainMappingElement {
        @JsonProperty("SourceDomainSet")
        public ArrayList<SourceDomainSet> sourceDomainSet;
        @JsonProperty("TargetDomainSet")
        public ArrayList<TargetDomainSet> targetDomainSet;
        @JsonProperty("Documentation")
        public Documentation documentation;
    }

    @Setter
    @Getter
    public static class DomainMappingElementsWithTransformation {
        @JsonProperty("DomainMappingElement")
        public DomainMappingElement domainMappingElement;
        @JsonProperty("Transformation")
        public Transformation transformation;
        @JsonProperty("Confidence")
        public double confidence;
        @JsonProperty("Confidential")
        public boolean confidential;
    }

    @Setter
    @Getter
    public static class Function {
        @JsonProperty("FunctionGuid")
        public String functionGuid;
        public boolean isReference;
        @JsonProperty("FunctionParameters")
        public ArrayList<FunctionParameter> functionParameters;
        @JsonProperty("FunctionImplementations")
        public ArrayList<FunctionImplementation> functionImplementations;
    }

    @Setter
    @Getter
    public static class FunctionImplementation {
        @JsonProperty("SourceCode")
        public String sourceCode;
        @JsonProperty("FunctionImplementationLanguage")
        public FunctionImplementationLanguage functionImplementationLanguage;
    }

    @Setter
    @Getter
    public static class FunctionImplementationLanguage {
        @JsonProperty("Name")
        public String name;
        @JsonProperty("Version")
        public String version;
    }

    @Setter
    @Getter
    public static class FunctionParameter {
        @JsonProperty("PositionInParameterList")
        public int positionInParameterList;
        @JsonProperty("Direction")
        public String direction;
        @JsonProperty("Type")
        public String type;
        @JsonProperty("Name")
        public String name;
    }

    @Setter
    @Getter
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
    }

    @Setter
    @Getter
    public static class Name {
        @JsonProperty("ArtifactValue")
        public ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class ParameterAssignment {
        @JsonProperty("PositionInParameterList")
        public int positionInParameterList;
        @JsonProperty("PositionInParameter")
        public int positionInParameter;
        @JsonProperty("PositionInDomainSet")
        public int positionInDomainSet;
    }


    @Setter
    @Getter
    public static class SourceBusinessContext {
        public String contextType;
        public String codeListId;
        public ArrayList<ContextValue> contextValues;
    }

    @Setter
    @Getter
    public static class SourceDomainSet {
        @JsonProperty("DomainGuid")
        public String domainGuid;
        @JsonProperty("Position")
        public int position;
    }

    @Setter
    @Getter
    public static class SourceMig {
        @JsonProperty("ObjectGUID")
        public String objectGUID;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
    }

    @Setter
    @Getter
    public static class Summary {
        @JsonProperty("ArtifactValue")
        public ArtifactValue artifactValue;
    }

    @Setter
    @Getter
    public static class TargetBusinessContext {
        public String contextType;
        public String codeListId;
        public ArrayList<ContextValue> contextValues;
    }

    @Setter
    @Getter
    public static class TargetDomainSet {
        @JsonProperty("DomainGuid")
        public String domainGuid;
        @JsonProperty("Position")
        public int position;
    }

    @Setter
    @Getter
    public static class TargetMig {
        @JsonProperty("ObjectGUID")
        public String objectGUID;
        @JsonProperty("TypeSystemId")
        public String typeSystemId;
    }

    @Setter
    @Getter
    public static class TargetNodeStatus {
    }

    @Setter
    @Getter
    public static class Transformation {
        @JsonProperty("Function")
        public Function function;
        @JsonProperty("ParameterAssignments")
        public ArrayList<ParameterAssignment> parameterAssignments;
    }

}
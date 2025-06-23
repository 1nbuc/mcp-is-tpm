package de.contriboot.mcptpm.api.entities.mig;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class MIGEntity {

    @JsonProperty("ArtifactMetadata")
    private ArtifactMetadata artifactMetadata;

    @JsonProperty("Identification")
    private Identification identification;

    @JsonProperty("HasEnvelope")
    private boolean hasEnvelope;

    @JsonProperty("IDProperties")
    private Map<String, Object> idProperties;

    @JsonProperty("Direction")
    private String direction;

    @JsonProperty("MessageRootDomainGuid")
    private String messageRootDomainGuid;

    @JsonProperty("MessageRootNodeParentXPath")
    private String messageRootNodeParentXPath;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("OwnBusinessContext")
    private List<BusinessContext> businessContext;

    @JsonProperty("PartnerBusinessContext")
    private List<BusinessContext> partnerBusinessContext;

    @JsonProperty("MessageTemplate")
    private MessageTemplate messageTemplate;

    @JsonProperty("RootNodeNSMigrated")
    private boolean rootNodeNSMigrated;

    @JsonProperty("RuntimeContext")
    private List<RuntimeContext> runtimeContext;

    @JsonProperty("VertexGUID")
    private String vertexGUID;

    @JsonProperty("AdministrativeData")
    private AdministrativeData administrativeData;

    @JsonProperty("Documentation")
    private Documentation documentation;

    @JsonProperty("Properties")
    private Map<String, PropertyDetail> properties;

    @JsonProperty("ComplexProperties")
    private List<Object> complexProperties;

    @JsonProperty("XmlNamespaces")
    private List<XmlNamespace> xmlNamespaces;

    @JsonProperty("Nodes")
    private List<Node> nodes;

    @JsonProperty("ComplexTypes")
    private Map<String, ComplexTypeDetail> complexTypes;

    @JsonProperty("SimpleTypes")
    private Map<String, SimpleTypeDetail> simpleTypes;

    @JsonProperty("STLocalCodelists")
    private Map<String, Object> stLocalCodelists;

    @JsonProperty("DocumentationArtifacts")
    private Map<String, String> documentationArtifacts;

    @JsonProperty("qualifiers")
    private List<Object> qualifiers;

    @JsonProperty("NamespaceHashes")
    private Map<String, Object> namespaceHashes;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    //@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class ArtifactMetadata {
        @JsonProperty("SchemaVersion")
        private String schemaVersion;
        @JsonProperty("ArtifactType")
        private String artifactType;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    //@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Identification {
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("MIGGUID")
        private String migGUID;
        @JsonProperty("ImportCorrelationGroupId")
        private String importCorrelationGroupId;
        @JsonProperty("ImportCorrelationObjectId")
        private String importCorrelationObjectId;
        @JsonProperty("MIGVersion")
        private String migVersion;
        @JsonProperty("Customer")
        private String customer;
        @JsonProperty("Tenant")
        private String tenant;
    }

    @Getter
    @Setter
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class CodelistIdentification {

        @JsonProperty("Category")
        private String category;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("TypeSystemId")
        private String typeSystemId;

        @JsonProperty("TypeSystemAcronym")
        private String typeSystemAcronym;

        @JsonProperty("VersionId")
        private String versionId;

        @JsonProperty("IsLatestVersion")
        private boolean isLatestVersion;

        @JsonProperty("Revision")
        private int revision;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class BusinessContext {
        @JsonProperty("contextType")
        private String contextType;
        @JsonProperty("codeListId")
        private String codeListId;
        @JsonProperty("contextValues")
        private List<ContextValue> contextValues;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class ContextValue {
        @JsonProperty("key")
        private String key;
        @JsonProperty("name")
        private String name;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class MessageTemplate {
        @JsonProperty("Documentation")
        private Documentation documentation;
        @JsonProperty("Status")
        private String status;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("TypeSystemId")
        private String typeSystemId;
        @JsonProperty("VersionAcronym")
        private String versionAcronym;
        @JsonProperty("TypeSystemAcronym")
        private String typeSystemAcronym;
        @JsonProperty("VersionId")
        private String versionId;
        @JsonProperty("ObjectGUID")
        private String objectGUID;
        @JsonProperty("Customer")
        private String customer;
        @JsonProperty("Tenant")
        private String tenant;
        @JsonProperty("IsCustomObject")
        private boolean isCustomObject;
        @JsonProperty("Revision")
        private int revision;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("DisplayTag")
        private String displayTag;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Documentation {
        @JsonProperty("Name")
        private Name name;
        @JsonProperty("Summary")
        private Summary summary;
        @JsonProperty("NumberOfNotes")
        private int numberOfNotes;
        @JsonProperty("Definition")
        private Definition definition;
        @JsonProperty("Notes")
        private List<Object> notes;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Name {
        @JsonProperty("BaseArtifactValue")
        private BaseArtifactValue baseArtifactValue;
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Summary {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Definition {
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
        @JsonProperty("BaseArtifactValue")
        private ArtifactValue baseArtifactValue;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class BaseArtifactValue {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("LanguageCode")
        private String languageCode;
        @JsonProperty("action")
        private String action;
        @JsonProperty("BaseArtifactValue")
        private BaseArtifactValue baseArtifactValue;
        @JsonProperty("PropertyName")
        private String propertyName;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class ArtifactValue {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("LanguageCode")
        private String languageCode;
        @JsonProperty("action")
        private String action;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class RuntimeContext {
        @JsonProperty("RuntimeName")
        private String runtimeName;
        @JsonProperty("PropertyDefinitions")
        private List<PropertyDefinition> propertyDefinitions;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class PropertyDefinition {
        @JsonProperty("PropertyName")
        private String propertyName;
        @JsonProperty("SelectedValue")
        private String selectedValue;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
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

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class XmlNamespace {
        @JsonProperty("Namespace")
        private String namespace;
        @JsonProperty("Prefix")
        private String prefix;
        @JsonProperty("IsDefault")
        private boolean isDefault;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Node {
        @JsonProperty("IsSelected")
        private boolean isSelected;
        @JsonProperty("CodelistReferenceDeselected")
        private boolean codelistReferenceDeselected;
        @JsonProperty("OverrideSimpleTypeCodelistReferences")
        private boolean overrideSimpleTypeCodelistReferences;
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("XMLNodeName")
        private String xmlNodeName;
        @JsonProperty("NodeCategory")
        private String nodeCategory;
        @JsonProperty("Documentation")
        private Documentation documentation;
        @JsonProperty("Domain")
        private Domain domain;
        @JsonProperty("ComplexTypeVertexGUID")
        private String complexTypeVertexGUID;
        @JsonProperty("BaseTypeDomain")
        private BaseTypeDomain baseTypeDomain;
        @JsonProperty("Qualifiers")
        private List<Object> qualifiers;
        @JsonProperty("NumberOfNodes")
        private Integer numberOfNodes; // Use Integer for nullable int
        @JsonProperty("Nodes")
        private List<Node> nodes; // Recursive
        @JsonProperty("Properties")
        private Map<String, PropertyDetail> properties;
        @JsonProperty("IDProperties")
        private Map<String, Object> idProperties;
        @JsonProperty("CodelistReferences")
        private List<CodelistReference> codelistRefrences;
        @JsonProperty("SelectedCodelist")
        private SelectedCodelist selectedCodelist;
        @JsonProperty("QualifierMarkers")
        private List<Object> qualifierMarkers;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("SimpleTypeVertexGUID")
        private String simpleTypeVertexGUID;
        @JsonProperty("NodeStatus")
        private NodeStatus nodeStatus;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectedCodelist {
        @JsonProperty("CodelistReference")
        private CodelistReference codelistReference;
        @JsonProperty("SelectedCodes")
        private List<SelectedCode> selectedCodes;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SelectedCode {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Documentation")
        private Documentation documentation;

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Domain {
        @JsonProperty("DomainGUID")
        private String domainGUID;
        @JsonProperty("ParentDomainGUID")
        private String parentDomainGUID;
        @JsonProperty("XPath")
        private String xPath;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodelistReference {

        @JsonProperty("VertexGUID")
        private String vertexGUID;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("TypeSystemId")
        private String typeSystemId;

        @JsonProperty("VersionId")
        private String versionId;

        @JsonProperty("VersionMode")
        private String versionMode; // Kann auch ein Enum sein, wenn Werte fest sind

        @JsonProperty("Properties")
        private Map<String, PropertyDetail> properties; // Map, da "VersionMode", "IsSelected" etc. dynamische Schlüssel sind
    }


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class BaseTypeDomain {
        @JsonProperty("DomainGUID")
        private String domainGUID;
        @JsonProperty("ParentDomainGUID")
        private String parentDomainGUID;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class PropertyDetail {
        @JsonProperty("BaseArtifactValue")
        private BaseArtifactValue baseArtifactValue;
        @JsonProperty("PropertyName")
        private String propertyName;
        @JsonProperty("IsReadOnly")
        private Boolean isReadOnly; // Use Boolean for nullable boolean
        @JsonProperty("ArtifactValue")
        private ArtifactValue artifactValue;
        @JsonProperty("PropertyDataType")
        private String propertyDataType;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class ComplexTypeDetail {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("IsLocal")
        private boolean isLocal;
        @JsonProperty("Documentation")
        private Documentation documentation;
        @JsonProperty("Properties")
        private Map<String, PropertyDetail> properties;
        @JsonProperty("ComplexProperties")
        private List<Object> complexProperties;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("IDProperties")
        private Map<String, Object> idProperties;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class SimpleTypeDetail {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("IsLocal")
        private boolean isLocal;
        @JsonProperty("FacetProperties")
        private Map<String, BaseArtifactValue> facetProperties;
        @JsonProperty("CodelistReferences")
        private List<Object> codelistReferences;
        @JsonProperty("Properties")
        private Map<String, PropertyDetail> properties;
        @JsonProperty("Documentation")
        private Documentation documentation;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("NumberOfCodelistReferences")
        private int numberOfCodeListReferences;
        @JsonProperty("PropertySets")
        private Object propertySets;
        @JsonProperty("IDProperties")
        private Map<String, Object> idProperties;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class FacetProperties {
        @JsonProperty("PrimitiveType")
        private BaseArtifactValue primitiveType;
        @JsonProperty("PropertyName")
        private String propertyName;
        @JsonProperty("MaxLength")
        private BaseArtifactValue maxLength;
        @JsonProperty("DateTimeFormat")
        private BaseArtifactValue dateTimeFormat;
    }

    @JsonProperty("LocalCodelists")
    private Map<String, Codelist> localCodelists;


    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Codelist {

        @JsonProperty("ArtifactMetadata")
        private ArtifactMetadata artifactMetadata;

        @JsonProperty("Identification")
        private CodelistIdentification identification;

        @JsonProperty("VertexGUID")
        private String vertexGUID;

        @JsonProperty("Tag")
        private String tag;

        @JsonProperty("Documentation")
        private Documentation documentation; // Hier die "Documentation" aus dem vorherigen Beispiel wiederverwenden

        @JsonProperty("NumberOfCodeValues")
        private int numberOfCodeValues;

        @JsonProperty("Codes")
        private List<Code> codes;

        @JsonProperty("DocumentationArtifacts")
        private Map<String, String> documentationArtifacts; // Schlüssel-Wert-Paare

        @JsonProperty("Action")
        private String action;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor 
//@JsonIgnoreProperties(ignoreUnknown = true)
    @AllArgsConstructor
    public static class Code {

        @JsonProperty("VertexGUID")
        private String vertexGUID;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("Documentation")
        private Documentation documentation;

        @JsonProperty("Action")
        private String action;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NodeStatus {
        @JsonProperty("Status")
        private String status;
        @JsonProperty("Comment")
        private String comment;
    }
}

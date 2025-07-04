package de.contriboot.mcptpm.api.entities.mig;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.figaf.integration.common.factory.HttpClientsFactory;
import de.contriboot.mcptpm.api.clients.TypeSystemClient;
import de.contriboot.mcptpm.api.entities.mag.MAGProposalRequest;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.JsonUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @JsonProperty("LocalCodelists")
    private Map<String, Codelist> localCodelists;

    /**
     * Get a 2D List of all Node elements in minimal form
     *
     * @param selectedOnly Weather all or only selected elements should get returned
     * @return the list of MinimalNode's
     */
    public List<MIGEntity.MinimalNode> getMinimalNodeList(boolean selectedOnly) {
        List<MIGEntity.MinimalNode> resultList = new ArrayList<>();

        for (MIGEntity.Node node : this.getNodes()) {
            if (selectedOnly && !node.isSelected()) {
                continue;
            }

            resultList.addAll(buildMinimalNodeListRecursive(node, resultList, selectedOnly));
        }

        return resultList;
    }

    public String getDocumentationById(String documentationId) {
        return this.getDocumentationArtifacts().get(documentationId);
    }

    public List<MAGProposalRequest.DomainGuid> getDomainGuidsWithCodeValues() {
        List<MAGProposalRequest.DomainGuid> resultList = new ArrayList<>();
        for (Node currNode : this.getAllSelectedNodesAsList()) {
            String domainGuid = currNode.getDomain().getDomainGUID();
            resultList.add(new MAGProposalRequest.DomainGuid(domainGuid));

            for (String codeValueGuid : getCodeValueGuidsForNode(currNode)) {
                resultList.add(new MAGProposalRequest.DomainGuid(domainGuid, codeValueGuid));
            }
        }

        return resultList;

    }

    public List<MIGEntity.MinimalNode> buildMinimalNodeListRecursive(MIGEntity.Node node, List<MIGEntity.MinimalNode> resultList, boolean selectedOnly) {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }

        MIGEntity.MinimalNode nodeObj = new MIGEntity.MinimalNode();
        nodeObj.setVertexGuid(node.getVertexGUID());
        nodeObj.setSelected(node.isSelected());
        nodeObj.setXPath(node.getDomain().getXPath());

        nodeObj.setHasChildren(!node.getNodes().isEmpty());


        nodeObj.setMinOccur(Integer.parseInt(node.getProperties().get("MinOccurs").getBaseArtifactValue().getId()));


        nodeObj.setMinOccur(Integer.parseInt(node.getProperties().get("MaxOccurs").getBaseArtifactValue().getId()));

        List<MIGEntity.CodelistReference> codelistReferences = new ArrayList<>();
        if (node.getCodelistRefrence() != null) codelistReferences.add(node.getCodelistRefrence());

        if (node.getSelectedCodelist() != null)
            codelistReferences.add(node.getSelectedCodelist().getCodelistReference());


        nodeObj.setCodelistRefrence(codelistReferences);

        resultList.add(nodeObj);

        String documentationId = JsonUtils.safeGet(() -> node.getDocumentation().getName().getBaseArtifactValue().getId());
        nodeObj.setDocumentation(getDocumentationById(documentationId));

        if (node.getSimpleTypeVertexGUID() != null) {
            nodeObj.setSimpleType(getSimpleType(node.getSimpleTypeVertexGUID()));
        }


        for (MIGEntity.Node subNode : node.getNodes()) {
            if (selectedOnly && !subNode.isSelected()) {
                continue;
            }
            try {
                buildMinimalNodeListRecursive(subNode, resultList, selectedOnly);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error while building minimal node list: " + e.getMessage());
            }
        }


        return resultList;
    }

    public Node getNodeByVertexGuidRecursive(String vertexGuid, Node currentNode) {
        if (currentNode.getVertexGUID().equals(vertexGuid)) {
            return currentNode;
        }

        for (MIGEntity.Node node : currentNode.getNodes()) {
            Node resultNode = getNodeByVertexGuidRecursive(vertexGuid, node);
            if (resultNode != null) {
                return resultNode;
            }
        }

        return null;
    }

    public Node getNodeByVertexGuid(String vertexGuid) {
        for (MIGEntity.Node node : this.getNodes()) {
            MIGEntity.Node foundNode = getNodeByVertexGuidRecursive(vertexGuid, node);
            if (foundNode != null) {
                return foundNode;
            }
        }

        throw new RuntimeException("Could not find node with vertex guid " + vertexGuid);
    }

    public MIGEntity.MinimalNode.SimpleType getSimpleType(String simpleTypeVertexGuid) {
        MIGEntity.MinimalNode.SimpleType minimalSimpleType = new MIGEntity.MinimalNode.SimpleType();

        MIGEntity.SimpleTypeDetail simpleType = this.getSimpleTypes().get(simpleTypeVertexGuid);

        minimalSimpleType.setId(simpleType.getId());

        String primitiveType = JsonUtils.safeGet(() -> simpleType.getFacetProperties().get("PrimitiveType").getBaseArtifactValue().getId());

        String maxLengthStr = JsonUtils.safeGet(() -> simpleType.getFacetProperties().get("MaxLength").getBaseArtifactValue().getId());


        String syntaxType = JsonUtils.safeGet(() -> simpleType.getProperties().get("SyntaxDataType").getBaseArtifactValue().getId());

        minimalSimpleType.setPrimitiveType(primitiveType);
        if (maxLengthStr != null) minimalSimpleType.setLength(Integer.parseInt(maxLengthStr));
        minimalSimpleType.setSyntaxType(syntaxType);

        return minimalSimpleType;

    }

    public void findAndChangeNodeSelection(String nodeVertexGUID, boolean selected) {
        for (MIGEntity.Node rootNode : this.getNodes()) {
            findAndChangeNodeSelectionRecursive(nodeVertexGUID, selected, rootNode);
        }
    }

    private boolean findAndChangeNodeSelectionRecursive(String nodeVertexGUID, boolean selected, MIGEntity.Node rootNode) {
        if (rootNode == null) {
            return false;
        }

        if (rootNode.getVertexGUID().equals(nodeVertexGUID)) {
            rootNode.setSelected(selected);
            return true;
        }

        for (MIGEntity.Node subNode : rootNode.getNodes()) {
            if (findAndChangeNodeSelectionRecursive(nodeVertexGUID, selected, subNode)) {
                return true;
            }
        }

        return false;
    }

    public MIGProposalRequest getMIGProposalRequest() {
        MIGProposalRequest request = new MIGProposalRequest();

        request.setDirection(this.getDirection());
        request.setBusinessContext(new ArrayList<>(this.getBusinessContext()));
        request.setPartnerBusinessContext(new ArrayList<>(this.getPartnerBusinessContext()));
        request.setRootNodeDomainGuid(this.getMessageRootDomainGuid());
        request.setMessageRootNodeParentXPath(this.getMessageRootNodeParentXPath());


        request.setIdentification(this.getIdentification());


        request.setMessageTemplate(this.getMessageTemplate());

        return request;
    }

    public void applyMIGProposalRequest(MIGProposalResponse proposal, float confidenceThreshold) {
        for (MIGProposalResponse.MigProposal proposalEntity : proposal.getMigProposal()) {
            findAndChangeNodeSelection(proposalEntity.getVertexGUID(), proposalEntity.getConfidenceValue() > confidenceThreshold);
        }
    }

    /**
     * Creates a CodelistReferenceDetail for a given node by checking for a
     * codelist in a specific order:
     * 1. Direct reference on the node.
     * 2. Reference via the node's SimpleType.
     * 3. Reference via the node's Qualifier.
     *
     * @param node The node to analyze.
     * @return A populated CodelistReferenceDetail object.
     */
    private CodelistReferenceDetail createCodelistDetailForNode(MIGEntity.Node node) {
        CodelistReferenceDetail detail = new CodelistReferenceDetail();

        // Case 1: The node has a direct, selected codelist reference.
        if (node.getSelectedCodelist() != null) {
            populateFromDirectReference(detail, node);
            return detail;
        }

        // Case 2: The node has a SimpleType that might contain a codelist reference.
        if (node.getSimpleTypeVertexGUID() != null) {
            populateFromSimpleType(detail, node);
            return detail;
        }

        // Case 3: The node has qualifiers that point to another node with a SimpleType.
        if (!node.getQualifierMarkers().isEmpty()) {
            populateFromQualifier(detail, node);
            return detail;
        }

        // Default case: No codelist reference found.
        detail.hasCodelistReference = false;
        return detail;
    }

    /**
     * Populates detail from the node's directly selected codelist.
     */
    private void populateFromDirectReference(CodelistReferenceDetail detail, MIGEntity.Node node) {
        detail.hasCodelistReference = true;
        detail.allValuesSelected = false; // Specific values are listed.
        detail.codelistReferenceId = node.getSelectedCodelist().getCodelistReference().getId();

        for (MIGEntity.SelectedCode selectedCode : node.getSelectedCodelist().getSelectedCodes()) {
            detail.getSelectedValues().add(selectedCode.getId());
        }
    }

    /**
     * Populates detail from the codelist found in the node's SimpleType.
     */
    private void populateFromSimpleType(CodelistReferenceDetail detail, MIGEntity.Node node) {
        SimpleTypeDetail simpleType = simpleTypes.get(node.getSimpleTypeVertexGUID());
        if (simpleType != null && !simpleType.getCodelistReferences().isEmpty()) {
            // Assuming only the first codelist reference is relevant
            CodelistReference codeListRef = simpleType.getCodelistReferences().get(0);
            detail.hasCodelistReference = true;
            detail.codelistReferenceId = codeListRef.getId();
            // TODO: The logic for 'allValuesSelected' from SimpleType is not implemented.
            // The original code threw an exception here.
            // Example: detail.allValuesSelected = isAllValuesSelected(codeListRef);
        }
    }

    /**
     * Populates detail from the codelist found via the node's qualifier.
     */
    private void populateFromQualifier(CodelistReferenceDetail detail, MIGEntity.Node node) {
        // Assuming only the first qualifier marker is relevant
        String qualifyingNodeVertexGUID = node.getQualifierMarkers().get(0).getQualifyingNodeVertexGUID();
        Node qualifyingNode = getNodeByVertexGuid(qualifyingNodeVertexGUID);

        if (qualifyingNode != null) {
            SimpleTypeDetail simpleType = simpleTypes.get(qualifyingNode.getSimpleTypeVertexGUID());
            if (simpleType != null && !simpleType.getCodelistReferences().isEmpty()) {
                // Assuming only the first codelist reference is relevant
                detail.hasCodelistReference = true;
                detail.allValuesSelected = true;
                detail.codelistReferenceId = simpleType.getCodelistReferences().get(0).getId();
            }
        }
    }

    private void getAllNodesAsListRecursive(Node node, List<Node> resultList, boolean selectedOnly) {
        if (node.isSelected() || (!selectedOnly)) {
            resultList.add(node);
        }

        for (Node subNode : node.getNodes()) {
            getAllNodesAsListRecursive(subNode, resultList, selectedOnly);
        }
    }

    /**
     * Function to easier iterate over all Nodes
     *
     * @return List of all Nodes but keeping references
     */
    private List<Node> getAllSelectedNodesAsList() {
        List<Node> result = new ArrayList<>();
        for (Node node : this.getNodes()) {
            getAllNodesAsListRecursive(node, new ArrayList<>(), true);
        }

        return result;
    }

    /**
     * Function to easier iterate over all Nodes
     *
     * @return List of all Nodes but keeping references
     */
    private List<Node> getAllNodesAsList() {
        List<Node> result = new ArrayList<>();
        for (Node node : this.getNodes()) {
            getAllNodesAsListRecursive(node, result, false);
        }

        return result;
    }

    private List<String> getAllDomainGuids() {
        List<String> result = new ArrayList<>();
        for (Node node : this.getAllSelectedNodesAsList()) {
            result.add(node.getDomain().getDomainGUID());
        }

        return result;
    }

    public Node getNodeByXPath(String xPath) {
        for (Node currNote : this.getAllNodesAsList()) {
            if (currNote.getDomain().getXPath().equals(xPath)) {
                return currNote;
            }
        }

        return null;
    }

    // TODO: maybe find a better solution to not call a client within an entity class
    private List<String> getCodeValueGuidsOfCodelist(CodelistReferenceDetail codelistReferenceDetail) {
        TypeSystemClient client = new TypeSystemClient(new HttpClientsFactory());
        return client.getCodeValueVertexIds(Config.getRequestContextFromEnv(), getMessageTemplate().getTypeSystemId(), getMessageTemplate().getVersionId(), codelistReferenceDetail.getCodelistReferenceId(), codelistReferenceDetail.getSelectedValues(), codelistReferenceDetail.isAllValuesSelected());
    }

    private List<String> getCodeValueGuidsForNode(Node node) {
        List<String> result = new ArrayList<>();
        CodelistReferenceDetail detail = new CodelistReferenceDetail();
        detail.hasCodelistReference = false;

        // Case 1: The node has a direct, selected codelist reference.
        if (node.getSelectedCodelist() != null) {
            populateFromDirectReference(detail, node);
        }

        // Case 2: The node has a SimpleType that might contain a codelist reference.
        if (node.getSimpleTypeVertexGUID() != null) {
            populateFromSimpleType(detail, node);
        }

        // Case 3: The node has qualifiers that point to another node with a SimpleType.
        if (!node.getQualifierMarkers().isEmpty()) {
            populateFromQualifier(detail, node);
        }

        if (detail.hasCodelistReference) {
            result = getCodeValueGuidsOfCodelist(detail);
        }

        return result;

    }

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
    @AllArgsConstructor
    public static class Node {
        @JsonProperty("IsSelected")
        private boolean isSelected;

        @JsonProperty("SelectedCodelist")
        private SelectedCodelist selectedCodelist;

        @JsonProperty("CodelistRefrences")
        private CodelistReference codelistRefrence;

        @JsonProperty("CodelistReferences")
        private List<CodelistReference> codelistReferences;

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
        @JsonProperty(value = "NodeStatus", required = true)
        private NodeStatus nodeStatus;
        @JsonProperty("Qualifiers")
        private List<String> qualifiers;
        @JsonProperty("NumberOfNodes")
        private int numberOfNodes; // Use int for nullable int
        @JsonProperty("Nodes")
        private List<Node> nodes; // Recursive
        @JsonProperty("Properties")
        private Map<String, PropertyDetail> properties;
        @JsonProperty("IDProperties")
        private Map<String, Object> idProperties;

        @JsonProperty("QualifierMarkers")
        private List<QualifierMarker> qualifierMarkers;
        @JsonProperty("Tag")
        private String tag;
        @JsonProperty("SimpleTypeVertexGUID")
        private String simpleTypeVertexGUID;
    }

    @Getter
    @Setter
    public static class QualifierMarker {
        @JsonProperty("VertexGUID")
        private String vertexGUID;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("QualifyingNodeVertexGUID")
        private String qualifyingNodeVertexGUID;
        @JsonProperty("CodelistReferenceVertexGUID")
        private String codelistReferenceVertexGUID;
        @JsonProperty("QualifierType")
        private String qualifierType;
        @JsonProperty("RelativeXPath")
        private String relativeXPath;
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
        private Object documentation;

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
        private Object properties; // Map, da "VersionMode", "IsSelected" etc. dynamische Schlüssel sind
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
        private List<MIGEntity.CodelistReference> codelistReferences;
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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinimalNode {

        @JsonProperty("VertexGUID")
        private String vertexGuid;

        @JsonProperty("IsSelected")
        private boolean isSelected;

        @JsonProperty("XPath")
        private String xPath;

        @JsonProperty("hasChildren")
        private boolean hasChildren;

        @JsonProperty("SimpleType")
        private MIGEntity.MinimalNode.SimpleType simpleType;
        @JsonProperty("codelistRefrence")
        private List<MIGEntity.CodelistReference> codelistRefrence = new ArrayList<>();

        @JsonProperty("minOccur")
        private int minOccur;
        @JsonProperty("maxOccur")
        private int maxOccur;
        @JsonProperty("documentation")
        private String documentation;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SimpleType {
            @JsonProperty("CodelistReferences")
            List<CodelistReference> codelistReferences;
            @JsonProperty("length")
            private int length;
            @JsonProperty("Id")
            private String id;
            @JsonProperty("primitiveType")
            private String primitiveType;
            @JsonProperty("syntaxType")
            private String syntaxType;

        }


    }

    @Getter
    @Setter
    public static class CodelistReferenceDetail {
        private boolean hasCodelistReference;
        private String codelistReferenceId;
        private boolean allValuesSelected;
        private List<String> selectedValues = new ArrayList<>();
    }
}

package de.contriboot.mcptpm.api.entities.mig;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.contriboot.mcptpm.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class MigEntityUtils {
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
        private SimpleType simpleType;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SimpleType {
            @JsonProperty("length")
            private int length;

            @JsonProperty("Id")
            private String id;

            @JsonProperty("primitiveType")
            private String primitiveType;

            @JsonProperty("syntaxType")
            private String syntaxType;

        };


        @JsonProperty("codelistRefrence")
        private List<MIGEntity.CodelistReference> codelistRefrence = new ArrayList<>();

        @JsonProperty("minOccur")
        private int minOccur;

        @JsonProperty("maxOccur")
        private int maxOccur;

        @JsonProperty("documentation")
        private String documentation;


    }



    private MIGEntity entity;

    public MigEntityUtils(MIGEntity entity) {
        this.entity = entity;
    }

    /**
     * Get a 2D List of all Node elements in minimal form
     * @param selectedOnly Weather all or only selected elements should get returned
     * @return the list of MinimalNode's
     */
    public List<MinimalNode> getMinimalNodeList(boolean selectedOnly) {
        List<MinimalNode> resultList = new ArrayList<>();

        for (MIGEntity.Node node : entity.getNodes()) {
            if (selectedOnly && !node.isSelected()) {
                continue;
            }

            resultList.addAll(
                    buildMinimalNodeListRecursive(node, resultList, selectedOnly)
            );
        }

        return resultList;
    }

    public String getDocumentationById(String documentationId) {
        return entity.getDocumentationArtifacts().get(documentationId);
    }

    public List<MinimalNode> buildMinimalNodeListRecursive(MIGEntity.Node node, List<MinimalNode> resultList, boolean selectedOnly) {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }

        MinimalNode nodeObj = new MinimalNode();
        nodeObj.setVertexGuid(node.getVertexGUID());
        nodeObj.setSelected(node.isSelected());
        nodeObj.setXPath(node.getDomain().getXPath());

        nodeObj.setHasChildren(!node.getNodes().isEmpty());


        nodeObj.setMinOccur(
                Integer.parseInt(
                        node.getProperties()
                                .get("MinOccurs")
                                .getBaseArtifactValue()
                                .getId()
                ));



        nodeObj.setMinOccur(
                Integer.parseInt(
                        node.getProperties()
                                .get("MaxOccurs")
                                .getBaseArtifactValue()
                                .getId()
                ));

        List<MIGEntity.CodelistReference> codelistReferences = new ArrayList<>();
        if (node.getCodelistRefrences() != null) codelistReferences.addAll(node.getCodelistRefrences());

        if (node.getSelectedCodelist() != null) codelistReferences.add(node.getSelectedCodelist().getCodelistReference());


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

    public MinimalNode.SimpleType getSimpleType(String simpleTypeVertexGuid) {
        MinimalNode.SimpleType minimalSimpleType = new MinimalNode.SimpleType();

        MIGEntity.SimpleTypeDetail simpleType = entity.getSimpleTypes().get(simpleTypeVertexGuid);

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
        for (MIGEntity.Node rootNode : entity.getNodes()) {
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

        request.setDirection(entity.getDirection());
        request.setBusinessContext(new ArrayList<>(entity.getBusinessContext()));
        request.setPartnerBusinessContext(new ArrayList<>(entity.getPartnerBusinessContext()));
        request.setRootNodeDomainGuid(entity.getMessageRootDomainGuid());
        request.setMessageRootNodeParentXPath(entity.getMessageRootNodeParentXPath());


        request.setIdentification(entity.getIdentification());


        request.setMessageTemplate(entity.getMessageTemplate());

        return request;
    }

    public void applyMIGProposalRequest(MIGProposalResponse proposal, float confidenceThreshold) {
        for(MIGProposalResponse.MigProposal proposalEntity : proposal.getMigProposal()) {
            if (proposalEntity.getConfidenceValue() > confidenceThreshold) {
                findAndChangeNodeSelection(proposalEntity.getVertexGUID(), true);
            } else {
                findAndChangeNodeSelection(proposalEntity.getVertexGUID(), false);
            }
        }
    }

}

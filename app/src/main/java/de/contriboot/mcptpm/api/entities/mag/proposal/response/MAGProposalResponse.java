package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MAGProposalResponse {

    @JsonProperty("metadata")
    private Metadata metadata;

    @JsonProperty("ProposedMappingElements")
    private List<ProposedMappingElementsItem> proposedMappingElements;

    @JsonProperty("Identification")
    private Identification identification;

    @JsonProperty("ArtifactMetadata")
    private ArtifactMetadata artifactMetadata;

    @JsonProperty("DomainMappingElementsWithTransformation")
    private List<DomainMappingElementsWithTransformationItem> domainMappingElementsWithTransformation;


}
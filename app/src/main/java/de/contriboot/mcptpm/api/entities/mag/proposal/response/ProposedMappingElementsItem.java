package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposedMappingElementsItem{

	@JsonProperty("ProposedTransformations")
	private List<ProposedTransformationsItem> proposedTransformations;

	@JsonProperty("DomainMappingElement")
	private DomainMappingElement domainMappingElement;

	@JsonProperty("Confidence")
	private Object confidence;
}
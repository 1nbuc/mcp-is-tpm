package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainMappingElement{

	@JsonProperty("TargetDomainSet")
	private List<TargetDomainSetItem> targetDomainSet;

	@JsonProperty("SourceDomainSet")
	private List<SourceDomainSetItem> sourceDomainSet;
}
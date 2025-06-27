package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceDomainSetItem{

	@JsonProperty("Position")
	private int position;

	@JsonProperty("DomainGuid")
	private String domainGuid;

	@JsonProperty("Roles")
	private List<String> roles;

	@JsonProperty("CodeValueGuid")
	private String codeValueGuid;
}
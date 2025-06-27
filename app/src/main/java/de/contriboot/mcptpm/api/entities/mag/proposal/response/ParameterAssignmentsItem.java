package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterAssignmentsItem{

	@JsonProperty("PositionInParameterList")
	private int positionInParameterList;

	@JsonProperty("PositionInDomainSet")
	private int positionInDomainSet;

	@JsonProperty("PositionInParameter")
	private int positionInParameter;

	@JsonProperty("Value")
	private String value;
}
package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionParametersItem{

	@JsonProperty("Type")
	private String type;

	@JsonProperty("PositionInParameterList")
	private int positionInParameterList;

	@JsonProperty("Direction")
	private String direction;

	@JsonProperty("Name")
	private String name;
}
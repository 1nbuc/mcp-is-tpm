package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionImplementationsItem{

	@JsonProperty("SourceCode")
	private String sourceCode;

	@JsonProperty("FunctionImplementationLanguage")
	private FunctionImplementationLanguage functionImplementationLanguage;
}
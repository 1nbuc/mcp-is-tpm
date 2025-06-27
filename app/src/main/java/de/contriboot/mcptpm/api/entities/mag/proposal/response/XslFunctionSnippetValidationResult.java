package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class XslFunctionSnippetValidationResult{

	@JsonProperty("isValid")
	private boolean isValid;

	@JsonProperty("warnings")
	private List<Object> warnings;

	@JsonProperty("errors")
	private List<Object> errors;
}
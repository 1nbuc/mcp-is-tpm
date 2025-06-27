package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class XslFunctionSnippetValidationResult {

    @JsonProperty("isValid")
    private boolean isValid;

    @JsonProperty("warnings")
    private List<Object> warnings;

    @JsonProperty("errors")
    private List<Object> errors;
}
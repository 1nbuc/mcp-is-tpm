package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposedTransformationsItem {

    @JsonProperty("Transformation")
    private Transformation transformation;

    @JsonProperty("Confidence")
    private Object confidence;

    @JsonProperty("XslFunctionSnippetValidationResult")
    private XslFunctionSnippetValidationResult xslFunctionSnippetValidationResult;
}
package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DomainMappingElementsWithTransformationItem {

    @JsonProperty("DomainMappingElement")
    private DomainMappingElement domainMappingElement;

    @JsonProperty("Transformation")
    private Transformation transformation;

    @JsonProperty("Confidence")
    private Object confidence;

    @JsonProperty("XslFunctionSnippetValidationResult")
    private XslFunctionSnippetValidationResult xslFunctionSnippetValidationResult;
}
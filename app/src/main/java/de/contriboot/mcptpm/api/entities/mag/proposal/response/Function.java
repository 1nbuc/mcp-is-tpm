package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Function {

    @JsonProperty("FunctionImplementations")
    private List<FunctionImplementationsItem> functionImplementations;

    @JsonProperty("isReference")
    private boolean isReference;

    @JsonProperty("FunctionParameters")
    private List<FunctionParametersItem> functionParameters;

    @JsonProperty("FunctionGuid")
    private String functionGuid;
}
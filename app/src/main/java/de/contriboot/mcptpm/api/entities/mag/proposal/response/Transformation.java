package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Transformation {

    @JsonProperty("ParameterAssignments")
    private List<ParameterAssignmentsItem> parameterAssignments;

    @JsonProperty("Function")
    private Function function;
}
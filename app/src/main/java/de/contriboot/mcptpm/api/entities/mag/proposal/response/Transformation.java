package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transformation{

	@JsonProperty("ParameterAssignments")
	private List<ParameterAssignmentsItem> parameterAssignments;

	@JsonProperty("Function")
	private Function function;
}
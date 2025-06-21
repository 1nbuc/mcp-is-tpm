package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class ActivityParameter {
    @JsonProperty("ParameterSource")
    private ParameterSource parameterSource = new ParameterSource();

    @JsonProperty("Direction")
    private String direction;

    @JsonProperty("Parameters")
    private Map<String, String> parameters;
}

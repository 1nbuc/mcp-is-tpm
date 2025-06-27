package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MAGSimulationRequest {
    @JsonProperty("mag")
    private MAGEntity mag;

    @JsonProperty("payload")
    private String payload;
}

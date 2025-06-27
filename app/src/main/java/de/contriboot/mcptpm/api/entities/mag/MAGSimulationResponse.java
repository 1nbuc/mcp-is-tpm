package de.contriboot.mcptpm.api.entities.mag;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


// only needing the targetpayload currently, so ignoring other props
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MAGSimulationResponse {
    @JsonProperty("targetPayload")
    private String targetPayload;
}
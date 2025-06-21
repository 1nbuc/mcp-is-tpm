package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParameterSource {
    @JsonProperty("SourceId")
    private String sourceId;

    @JsonProperty("SourceName")
    private String sourceName;

    @JsonProperty("ArtifactType")
    private String artifactType;
}

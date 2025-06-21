package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChoreographyMappingProperties {
    @JsonProperty("Properties")
    private MappingDetails properties = new MappingDetails();
}

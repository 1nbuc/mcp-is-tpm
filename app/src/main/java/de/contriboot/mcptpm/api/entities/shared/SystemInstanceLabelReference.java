package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemInstanceLabelReference {
    @JsonProperty("Properties")
    private SystemInstanceLabelProperties properties = new SystemInstanceLabelProperties();
}

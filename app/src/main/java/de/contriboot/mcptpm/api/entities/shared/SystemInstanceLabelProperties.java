package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemInstanceLabelProperties {
    @JsonProperty("Label_SystemInstanceName")
    private String labelSystemInstanceName;

    @JsonProperty("Label_Purpose")
    private String labelPurpose;
}

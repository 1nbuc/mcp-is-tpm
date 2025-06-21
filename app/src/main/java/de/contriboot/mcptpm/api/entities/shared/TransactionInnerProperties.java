package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionInnerProperties {
    @JsonProperty("Label_ReactorName")
    private String labelReactorName;

    @JsonProperty("Reactor")
    private String reactor;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Label_InitiatorName")
    private String labelInitiatorName;
}

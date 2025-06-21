package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChoreographySenderAdapterProperties {
    @JsonProperty("Properties")
    private SenderAdapterDetails properties = new SenderAdapterDetails();
}

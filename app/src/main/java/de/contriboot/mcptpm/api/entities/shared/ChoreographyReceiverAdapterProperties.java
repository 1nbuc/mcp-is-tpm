package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChoreographyReceiverAdapterProperties {
    @JsonProperty("Properties")
    private ReceiverAdapterDetails properties = new ReceiverAdapterDetails();
}

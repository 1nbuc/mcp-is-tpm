package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChoreographyInnerProperties {
    @JsonProperty("SENDER_SYSTEM")
    private ChoreographySystemProperties senderSystem = new ChoreographySystemProperties();

    @JsonProperty("SENDER_ADAPTER")
    private ChoreographySenderAdapterProperties senderAdapter = new ChoreographySenderAdapterProperties();

    @JsonProperty("SENDER_INTERCHANGE")
    private ChoreographySenderInterchangeProperties senderInterchange = new ChoreographySenderInterchangeProperties();

    @JsonProperty("MAPPING")
    private ChoreographyMappingProperties mapping = new ChoreographyMappingProperties();

    @JsonProperty("RECEIVER_INTERCHANGE")
    private ChoreographyReceiverInterchangeProperties receiverInterchange = new ChoreographyReceiverInterchangeProperties();

    @JsonProperty("RECEIVER_ADAPTER")
    private ChoreographyReceiverAdapterProperties receiverAdapter = new ChoreographyReceiverAdapterProperties();

    @JsonProperty("RECEIVER_SYSTEM")
    private ChoreographySystemProperties receiverSystem = new ChoreographySystemProperties();
}

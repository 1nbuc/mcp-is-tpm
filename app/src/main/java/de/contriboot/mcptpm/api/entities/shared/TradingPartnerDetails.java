package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradingPartnerDetails {
    @JsonProperty("IdForTradingPartner")
    private IdReference idForTradingPartner = new IdReference();

    @JsonProperty("IdForSystemInstance")
    private SystemInstanceReference idForSystemInstance = new SystemInstanceReference();

    @JsonProperty("IdForSenderIdentifier")
    private IdReference idForSenderIdentifier = new IdReference();

    @JsonProperty("IdForReceiverIdentifier")
    private IdReference idForReceiverIdentifier = new IdReference();

    @JsonProperty("IdForContactPerson")
    private ContactPersonReference idForContactPerson = new ContactPersonReference();
}

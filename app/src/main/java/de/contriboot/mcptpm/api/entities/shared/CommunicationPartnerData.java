package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommunicationPartnerData {
    @JsonProperty("IdForTradingPartner")
    private LabelReference idForTradingPartner = new LabelReference();

    @JsonProperty("IdForSystemInstance")
    private SystemInstanceLabelReference idForSystemInstance = new SystemInstanceLabelReference();
}

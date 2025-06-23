package de.contriboot.mcptpm.api.entities.mig;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MIGProposalRequest {
    @JsonProperty("Direction")
    public String direction;
    @JsonProperty("OwnBusinessContext")
    public ArrayList<MIGEntity.BusinessContext> businessContext;
    @JsonProperty("PartnerBusinessContext")
    public ArrayList<MIGEntity.BusinessContext> partnerBusinessContext;
    @JsonProperty("Identification")
    public MIGEntity.Identification identification;
    @JsonProperty("MessageTemplate")
    public MIGEntity.MessageTemplate messageTemplate;
    @JsonProperty("RootNodeDomainGuid")
    public String rootNodeDomainGuid;
    @JsonProperty("MessageRootNodeParentXPath")
    public String messageRootNodeParentXPath;

}
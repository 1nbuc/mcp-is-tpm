package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetDomainSetItem {

    @JsonProperty("Position")
    private int position;

    @JsonProperty("DomainGuid")
    private String domainGuid;

    @JsonProperty("CodeValueGuid")
    private String codeValueGuid;
}
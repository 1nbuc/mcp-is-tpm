package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DomainMappingElement {

    @JsonProperty("TargetDomainSet")
    private List<TargetDomainSetItem> targetDomainSet;

    @JsonProperty("SourceDomainSet")
    private List<SourceDomainSetItem> sourceDomainSet;
}
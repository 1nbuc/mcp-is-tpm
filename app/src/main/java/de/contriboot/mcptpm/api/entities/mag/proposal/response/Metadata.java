package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {

    @JsonProperty("requestTime")
    private long requestTime;

    @JsonProperty("status")
    private String status;
}
package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification {

    @JsonProperty("ImportCorrelationObjectId")
    private String importCorrelationObjectId;

    @JsonProperty("Customer")
    private String customer;

    @JsonProperty("MagProposalRequestId")
    private String magProposalRequestId;

    @JsonProperty("MAGGUID")
    private String mAGGUID;

    @JsonProperty("ImportCorrelationGroupId")
    private String importCorrelationGroupId;

    @JsonProperty("ObjectGuid")
    private String objectGuid;

    @JsonProperty("MAGVersion")
    private String mAGVersion;
}
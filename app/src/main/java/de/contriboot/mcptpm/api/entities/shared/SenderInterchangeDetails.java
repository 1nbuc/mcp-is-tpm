package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SenderInterchangeDetails {
    @JsonProperty("Validation")
    private String validation;

    @JsonProperty("ImportCorrelationObjectId")
    private String importCorrelationObjectId;

    @JsonProperty("TypeSystem")
    private String typeSystem;

    @JsonProperty("Label_MIGIdName")
    private String labelMIGIdName;

    @JsonProperty("ObjectGUID")
    private String objectGUID;

    @JsonProperty("HasEnvelope")
    private String hasEnvelope;

    @JsonProperty("MIGGUID")
    private String migguid;

    @JsonProperty("Label_MIGStatus")
    private String labelMIGStatus;

    @JsonProperty("CreateAcknowledgement")
    private String createAcknowledgement;

    @JsonProperty("FunctionalAck")
    private String functionalAck;

    @JsonProperty("ImportCorrelationGroupId")
    private String importCorrelationGroupId;

    @JsonProperty("MIGLink")
    private String migLink;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("MessageType")
    private String messageType;

    @JsonProperty("TypeSystemVersion")
    private String typeSystemVersion;

    @JsonProperty("MIGVersionId")
    private String migVersionId;

    @JsonProperty("Label_TypeSystemName")
    private String labelTypeSystemName;
}

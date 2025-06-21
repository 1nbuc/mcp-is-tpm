package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SystemDetails {
    @JsonProperty("TypeSystem")
    private String typeSystem;

    @JsonProperty("SystemInstanceId")
    private String systemInstanceId;

    @JsonProperty("IdAsSenderAlias")
    private String idAsSenderAlias;

    @JsonProperty("SystemInstanceAlias")
    private String systemInstanceAlias;

    @JsonProperty("IdAsReceiver")
    private String idAsReceiver;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("TypeSystemVersion")
    private String typeSystemVersion;

    @JsonProperty("Label_Name")
    private String labelName;

    @JsonProperty("Label_IdAsReceiverName")
    private String labelIdAsReceiverName;

    @JsonProperty("Label_ReceiverSchemeCode")
    private String labelReceiverSchemeCode;

    @JsonProperty("Label_ReceiverSchemeName")
    private String labelReceiverSchemeName;

    @JsonProperty("Label_ReceiverTypeSystemId")
    private String labelReceiverTypeSystemId;

    @JsonProperty("Label_ReceiverTypeSystemName")
    private String labelReceiverTypeSystemName;

    @JsonProperty("Label_SystemInstanceName")
    private String labelSystemInstanceName;

    @JsonProperty("Label_Purpose")
    private String labelPurpose;
}

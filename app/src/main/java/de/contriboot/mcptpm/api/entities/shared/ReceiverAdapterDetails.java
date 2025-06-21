package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceiverAdapterDetails {
    @JsonProperty("CommunicationChannelTemplateId")
    private String communicationChannelTemplateId;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("ACKCommunicationChannelTemplateId")
    private String ackCommunicationChannelTemplateId;

    @JsonProperty("Label_CommunicationChannelTemplateName")
    private String labelCommunicationChannelTemplateName;

    @JsonProperty("Label_ACKCommunicationChannelTemplateName")
    private String labelACKCommunicationChannelTemplateName;
}

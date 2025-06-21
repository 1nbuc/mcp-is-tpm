package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SenderAdapterDetails {
    @JsonProperty("CommunicationChannelTemplateAlias")
    private String communicationChannelTemplateAlias;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Label_CommunicationChannelTemplateName")
    private String labelCommunicationChannelTemplateName;

    @JsonProperty("Label_CCTForSenderFunctionalACKName")
    private String labelCCTForSenderFunctionalACKName;
}

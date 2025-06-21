package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MappingDetails {
    @JsonProperty("MAGVersionId")
    private String magVersionId;

    @JsonProperty("ImportCorrelationObjectId")
    private String importCorrelationObjectId;

    @JsonProperty("Label_MAGName")
    private String labelMAGName;

    @JsonProperty("MAGGUID")
    private String magguid;

    @JsonProperty("ImportCorrelationGroupId")
    private String importCorrelationGroupId;

    @JsonProperty("MAGLink")
    private String magLink;

    @JsonProperty("ObjectGUID")
    private String objectGUID;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Label_MAGStatus")
    private String labelMAGStatus;
}

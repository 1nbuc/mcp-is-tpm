package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdministrativeData {
    @JsonProperty("createdAt")
    private long createdAt;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("modifiedAt")
    private long modifiedAt;

    @JsonProperty("modifiedBy")
    private String modifiedBy;




}

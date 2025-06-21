package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusinessTransactionActivityReference {
    @JsonProperty("Id")
    private String id;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("Name")
    private String name;
}

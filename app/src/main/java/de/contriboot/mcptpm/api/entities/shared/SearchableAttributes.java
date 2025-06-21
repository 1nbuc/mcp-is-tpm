package de.contriboot.mcptpm.api.entities.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SearchableAttributes {
    @JsonProperty("CUSTOM_SEARCH_ATTRIBUTE")
    private List<String> customSearchAttribute = new ArrayList<>();

    @JsonProperty("TENANT")
    private List<String> tenant = new ArrayList<>();
}

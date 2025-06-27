package de.contriboot.mcptpm.api.entities.mag.proposal.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionImplementationLanguage {

    @JsonProperty("Version")
    private String version;

    @JsonProperty("Name")
    private String name;
}
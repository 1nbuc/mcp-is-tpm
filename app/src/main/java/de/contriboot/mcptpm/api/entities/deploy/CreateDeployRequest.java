package de.contriboot.mcptpm.api.entities.deploy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CreateDeployRequest {
    @JsonProperty("Action")
    private String action = "DEPLOY_V2";
    @JsonProperty("Description")
    private String description = "";
    @JsonProperty("TaskInput")
    private TaskInput taskInput;
    @JsonProperty("TaskParameters")
    private TaskParameters taskParameters;



    @Getter
    @Setter
    public static class TaskInput {
        private String id;
        private String uniqueId;
        private String artifactType;
        private String displayName;
        private String semanticVersion;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TaskParameters {
        @JsonProperty("BT_LIST")
        private List<String> bT_LIST = new ArrayList<>();
    }

}
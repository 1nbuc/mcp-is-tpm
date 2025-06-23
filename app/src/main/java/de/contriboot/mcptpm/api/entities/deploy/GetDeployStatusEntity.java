package de.contriboot.mcptpm.api.entities.deploy;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDeployStatusEntity {
    @JsonProperty("TaskInput")
    private TaskInput taskInput;
    @JsonProperty("TaskId")
    private String taskId;
    @JsonProperty("TriggeredBy")
    private String triggeredBy;
    @JsonProperty("executionStatus")
    private ExecutionStatus executionStatus;
    @JsonProperty("taskResult")
    private TaskResult taskResult;

    @Getter
    @Setter
    public static class TaskResult {
        @JsonProperty("result")
        private boolean result;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExecutionStatus {
        @JsonProperty("Status")
        private String status;
        @JsonProperty("PercentComplete")
        private int percentComplete;
        @JsonProperty("ErrorMessage")
        private String errorMessage;
        private long completedAt;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskInput {
        @JsonProperty("Action")
        private String action;
        @JsonProperty("Description")
        private String description;
        @JsonProperty("TaskParameters")
        private TaskParameters taskParameters;
        @JsonProperty("TaskInput")
        private TaskInput taskInput;
        private String uniqueId;
        private String displayName;
        private String semanticVersion;
        private String id;
        private String artifactType;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskParameters {
        @JsonProperty("BT_LIST")
        private ArrayList<Object> bT_LIST;
    }

}
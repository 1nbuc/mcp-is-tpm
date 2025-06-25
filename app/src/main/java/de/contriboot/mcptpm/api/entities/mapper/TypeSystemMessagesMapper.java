package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;
import de.contriboot.mcptpm.utils.JsonUtils;

import java.util.List;

public class TypeSystemMessagesMapper {
    // To reduce context window size
    public static JsonNode fromJsonStringMinified(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode responseEntity = (ObjectNode) objectMapper.readTree(jsonString);
            ArrayNode rawMessages = (ArrayNode) responseEntity.get("Message");

            for (JsonNode rawMessage : rawMessages) {
                ((ObjectNode) rawMessage).put("Revisions", (JsonNode) null);
                ((ObjectNode) rawMessage).put("VersionIds", (JsonNode) null);

                String docId = JsonUtils.safeGet(() -> rawMessage.get("Documentation").get("Name").get("BaseArtifactValue").get("Id").asText());
                if(docId != null) ((ObjectNode) rawMessage).put(
                        "Documentation",
                        responseEntity.get("DocumentationArtifacts").get(docId));
            }

            responseEntity.put("DocumentationArtifacts", (ObjectNode) null);

            return responseEntity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to JsonNode", e);
        }
    }

}

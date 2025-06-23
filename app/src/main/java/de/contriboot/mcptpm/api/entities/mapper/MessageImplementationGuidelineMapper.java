package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.contriboot.mcptpm.api.clients.MigClientExtended;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import de.contriboot.mcptpm.api.entities.mig.MigEntityUtils;

import java.util.List;

public class MessageImplementationGuidelineMapper {



    public static MIGEntity fromJsonString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return objectMapper.readValue(jsonString, MIGEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to MIGEntity", e);
        }
    }

    public static String toJsonString(MIGEntity entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping MIGEntity to JSON string", e);
        }
    }

    public static String minimalNodeListToJSONString(List<MigEntityUtils.MinimalNode> entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping MIGEntity to JSON string", e);
        }
    }
}

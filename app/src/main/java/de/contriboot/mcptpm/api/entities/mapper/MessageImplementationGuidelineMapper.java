package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.mig.MIGEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MessageImplementationGuidelineMapper {


    public static MIGEntity fromJsonString(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonString, MIGEntity.class);
        } catch (Exception e) {
            log.error("Error mapping JSON string to MIGEntity", e);
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

    public static String minimalNodeListToJSONString(List<MIGEntity.MinimalNode> entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping MIGEntity to JSON string", e);
        }
    }
}

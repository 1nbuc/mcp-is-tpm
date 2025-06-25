package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.mag.MAGEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MAGResponseMapper {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static MAGEntity fromJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, MAGEntity.class);
        } catch (Exception e) {
            log.error("Error mapping JSON string to MAGResponse", e);
            throw new RuntimeException("Error mapping JSON string to MAGResponse", e);
        }
    }

    public static String toJsonString(MAGEntity entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            log.error("Error mapping MAGResponse to JSON string", e);
            throw new RuntimeException("Error mapping MAGResponse to JSON string", e);
        }
    }
}

package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.mag.MAGSimulationResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MAGSimulationResponseMapper {
    public static MAGSimulationResponse fromJsonString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            return objectMapper.readValue(jsonString, MAGSimulationResponse.class);
        } catch (Exception e) {
            log.error("Error mapping JSON string to MAGSimResponse", e);
            throw new RuntimeException("Error mapping JSON string to MAGSimResponse", e);
        }
    }
}

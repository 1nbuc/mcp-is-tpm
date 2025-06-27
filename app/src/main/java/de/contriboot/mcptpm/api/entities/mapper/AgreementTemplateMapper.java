package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgreementTemplateMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AgreementTemplateEntity fromJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, AgreementTemplateEntity.class);
        } catch (Exception e) {
            log.error("Error mapping AgreementTemplateEntity from JSON string", e);
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(AgreementTemplateEntity entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            log.error("Error mapping AgreementTemplateEntity to JSON string", e);
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

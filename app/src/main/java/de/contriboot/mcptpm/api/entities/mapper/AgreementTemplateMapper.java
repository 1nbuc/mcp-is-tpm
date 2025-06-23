package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;

public class AgreementTemplateMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AgreementTemplateEntity fromJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, AgreementTemplateEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(AgreementTemplateEntity entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

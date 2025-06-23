package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.PartnerSystemEntity;

import java.util.List;

public class SystemRequestMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<PartnerSystemEntity> fromJsonArraListString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<PartnerSystemEntity>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(PartnerSystemEntity entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

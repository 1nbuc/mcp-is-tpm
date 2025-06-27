package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.typeSystem.AllTypeSystemsResponse;

import java.util.List;

public class TypeSystemsMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Remove image file payload because it uses way too much tokens
    public static List<AllTypeSystemsResponse> fromJsonString(String jsonString) {
        try {
            List<AllTypeSystemsResponse> responseEntity = objectMapper.readValue(jsonString, new TypeReference<List<AllTypeSystemsResponse>>() {
            });
            responseEntity.stream().forEach(elem -> elem.setLogo(null));
            return responseEntity;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(AllTypeSystemsResponse entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

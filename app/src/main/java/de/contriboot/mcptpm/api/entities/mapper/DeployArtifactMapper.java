package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.deploy.GetDeployStatusEntity;

public class DeployArtifactMapper {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static GetDeployStatusEntity fromJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, GetDeployStatusEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(GetDeployStatusEntity entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.AgreementTemplateEntity;
import de.contriboot.mcptpm.api.entities.mig.MIGProposalResponse;


public class MIGResponseMapper {
    public static MIGProposalResponse fromJsonString(String jsonString) {


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, MIGProposalResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON string to AgreementTemplateEntity", e);
        }
    }

    public static String toJsonString(MIGProposalResponse entity) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

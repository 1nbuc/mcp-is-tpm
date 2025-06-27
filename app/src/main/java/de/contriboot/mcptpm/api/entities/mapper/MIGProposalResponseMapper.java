package de.contriboot.mcptpm.api.entities.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contriboot.mcptpm.api.entities.mig.MIGProposalResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MIGProposalResponseMapper {
    public static MIGProposalResponse fromJsonString(String jsonString) {


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, MIGProposalResponse.class);
        } catch (Exception e) {
            log.error("Error converting to MIG Response", e);
            throw new RuntimeException("Error mapping JSON string to MIGProposalResponse", e);
        }
    }

    public static String toJsonString(MIGProposalResponse entity) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(entity);
        } catch (Exception e) {
            log.error("Error converting from MIG Response", e);
            throw new RuntimeException("Error mapping AgreementTemplateEntity to JSON string", e);
        }
    }
}

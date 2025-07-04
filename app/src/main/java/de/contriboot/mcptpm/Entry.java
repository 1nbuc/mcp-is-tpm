package de.contriboot.mcptpm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import de.contriboot.mcptpm.handlers.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Entry {

    public static void main(String[] args) {
        SpringApplication.run(Entry.class, args);
    }

    @Bean
    public List<ToolCallback> getTools(
            PartnerTools partnerTools,
            AgreementTools agreementTools,
            BusinessDocumentTools busDocTools,
            CompanyProfileTools companyProfileTools,
            AgreementTemplateTools agreementTemplateTools,
            MessageImplementationGuidelinesTools messageImplementationGuidelinesTools,
            TypeSystemTools typeSystemTools,
            MappingGuidelinesTools mappingGuidelinesTools
    ) throws JsonProcessingException {
        return List.of(ToolCallbacks.from(
                partnerTools,
                agreementTools,
                busDocTools,
                companyProfileTools,
                agreementTemplateTools,
                messageImplementationGuidelinesTools,
                typeSystemTools,
                mappingGuidelinesTools
        ));
    }

    @Bean
    public MethodToolCallback getCustomTools() throws JsonMappingException {
        return AgreementTools.getUpdateB2BScenarioTool();
    }

}
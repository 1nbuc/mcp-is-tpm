package de.contriboot.mcptpm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.figaf.integration.common.entity.RequestContext;
import de.contriboot.mcptpm.handlers.*;
import de.contriboot.mcptpm.utils.Config;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            MessageImplementationGuidelinesTools mesImplGuidelinesTools,
            TypeSystemTools typeSystemTools
    ) throws JsonProcessingException {
        return List.of(ToolCallbacks.from(
                partnerTools,
                agreementTools,
                busDocTools,
                companyProfileTools,
                agreementTemplateTools,
                mesImplGuidelinesTools,
                typeSystemTools

        ));
    }

    @Bean
    public MethodToolCallback getCustomTools() throws JsonMappingException {
        return AgreementTools.getUpdateB2BScenarioTool();
    }

}
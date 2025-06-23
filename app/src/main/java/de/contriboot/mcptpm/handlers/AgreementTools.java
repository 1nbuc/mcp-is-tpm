package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.api.clients.AgreementClientExtended;
import de.contriboot.mcptpm.api.clients.B2BScenarioClientExtended;
import de.contriboot.mcptpm.api.entities.AgreementEntitiy;
import de.contriboot.mcptpm.api.entities.B2BScenarioEntity;
import de.contriboot.mcptpm.api.entities.deploy.GetDeployStatusEntity;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class AgreementTools {

    private AgreementClientExtended client;
    private B2BScenarioClientExtended clientb2b;

    public AgreementTools() {
        this.client = new AgreementClientExtended(new HttpClientsFactory());
        this.clientb2b = new B2BScenarioClientExtended(new HttpClientsFactory());
    }

    @Tool(name = "get-all-agreement-metadata", description = "Get metadata for all agreements.")
    public List<TpmObjectMetadata> getAllAgreementMetadata() {
        return client.getAllMetadata(Config.getRequestContextFromEnv());
    }

    @Tool(name = "get-agreement-b2b-scenario", description = "Get the technical b2b scenario of an agreement")
    public String getB2BScenario(String agreementId) {
        List<TpmObjectMetadata> agreementList = client.getAllMetadata(Config.getRequestContextFromEnv());
        TpmObjectMetadata selectedAgreement = agreementList.stream()
                .filter(agreement -> agreement.getObjectId().equals(agreementId))
                .findFirst().orElseThrow();

        return clientb2b.getB2BScenariosForAgreementRaw(Config.getRequestContextFromEnv(), selectedAgreement);
    }


    public String updateB2BScenario(B2BScenarioEntity b2bScenarioEntity) {

        clientb2b.updateB2BScenario(Config.getRequestContextFromEnv(), b2bScenarioEntity);
        return "success";
    }

    public static MethodToolCallback getUpdateB2BScenarioTool() throws JsonMappingException {
        // Find the method that now accepts a String argument
        Method method = ReflectionUtils.findMethod(AgreementTools.class, "updateB2BScenario", B2BScenarioEntity.class);

        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinition.builder()
                        .name("update-b2b-scenario")
                        .description("Update Agreements B2B Scenario")
                        .inputSchema(ToolUtils.getJsonSchema(B2BScenarioEntity.class, "b2bScenarioEntity"))
                        .build())
                .toolMethod(method)
                .toolObject(new AgreementTools())
                .build();
    }

    @Tool(name = "create-agreement-with-bound-template", description = "Create a new b2b agreement which is bound to a template. Recommended over copy-template")
    public AgreementEntitiy createAgreement(
            String agreementName,
            String description,
            String agreementTemplateId, // cd58f1a766d549018a48d71a2be604dc
            List<String> transactionIds, //4df9c28c265b4310baeaf060701e95f0
            String partnerId) { // 994a90074f22451bb539acfd02d669b1
        AgreementEntitiy agreementEntity = client.buildNewAgreementEntityByBoundTemplate(agreementName, description, agreementTemplateId, transactionIds, partnerId);
        return client.createAgreement(Config.getRequestContextFromEnv(), agreementEntity);
    }

    @Tool(name = "trigger-agreement-activate-or-update-deployment")
    public GetDeployStatusEntity updateOrDeployAgreement(
            String agreementId, List<String> transactionIds,
            @ToolParam(description = "false if is draft activation, true for updating existing active resource") boolean isUpdate) {
        return client.deployArtifactAndWait(Config.getRequestContextFromEnv(), agreementId, transactionIds, isUpdate);
    }
}

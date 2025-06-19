package de.contriboot.mcptpm.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.figaf.integration.common.factory.HttpClientsFactory;
import com.figaf.integration.tpm.client.agreement.AgreementClient;
import com.figaf.integration.tpm.client.b2bscenario.B2BScenarioClient;
import com.figaf.integration.tpm.entity.B2BScenarioMetadata;
import com.figaf.integration.tpm.entity.TpmObjectMetadata;
import de.contriboot.mcptpm.api.B2BScenarioClientExtended;
import de.contriboot.mcptpm.api.entities.B2BScenarioEntity;
import de.contriboot.mcptpm.utils.Config;
import de.contriboot.mcptpm.utils.ToolUtils;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class AgreementTools {

    private AgreementClient client;
    private B2BScenarioClientExtended clientb2b;

    public AgreementTools() {
        this.client = new AgreementClient(new HttpClientsFactory());
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
        Method method = ReflectionUtils.findMethod(AgreementTools.class, "updateB2BScenario", B2BScenarioEntity.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaGenerator generator = new JsonSchemaGenerator(objectMapper);

        return MethodToolCallback.builder()
                .toolDefinition(ToolDefinition.builder()
                        .name("update-b2b-scenario")
                        .description("Update Agreements B2B Scenario")
                        .inputSchema(ToolUtils.getJsonSchema(B2BScenarioEntity.class))
                        .build())
                .toolMethod(method)
                .toolObject(new AgreementTools())
                .build();
    }

}

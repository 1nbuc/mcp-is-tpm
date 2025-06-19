package de.contriboot.mcptpm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import de.contriboot.mcptpm.api.entities.B2BScenarioEntity;
import de.contriboot.mcptpm.handlers.AgreementTools;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

public class ToolUtils {
    public static ToolCallback getToolCallback(Class clazz, String methodName, String schema, Object bean, Class... parameterTypes) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        ToolDefinition toolDefinition = ToolDefinition.builder()
                .name("update-b2b-scenario")
                .description("Update B2B Scenario")
                .inputSchema(schema)
                .build();

        ToolCallback toolCallback = MethodToolCallback.builder()
                .toolDefinition(toolDefinition)
                .toolMethod(method)
                .toolObject(bean)
                .build();
        return toolCallback;
    }

    public static String getJsonSchema(Class clazz) {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
                .without(Option.FLATTENED_ENUMS_FROM_TOSTRING)
                .build();

        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(clazz);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(jsonSchema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

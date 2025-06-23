package de.contriboot.mcptpm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.victools.jsonschema.generator.*;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;

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

    public static <T> String getJsonSchema(Class clazz, String parameterName) {
        ObjectMapper objectMapper = new ObjectMapper();
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig config = configBuilder.with(Option.EXTRA_OPEN_API_FORMAT_VALUES)
                .without(Option.FLATTENED_ENUMS_FROM_TOSTRING)
                .build();

        SchemaGenerator generator = new SchemaGenerator(config);
        // 1. Generate the schema for the class itself
        JsonNode generatedSchemaForClass = generator.generateSchema(clazz);

        // 2. Create a new root schema object
        ObjectNode rootSchema = objectMapper.createObjectNode();
        rootSchema.put("type", "object");

        // 3. Add a "properties" field to the new root schema
        ObjectNode propertiesNode = objectMapper.createObjectNode();
        rootSchema.set("properties", propertiesNode);

        // 4. Inside "properties", add a property named "parameterName"
        //    whose value is either a $ref to the definition of clazz or the direct schema for simple types
        JsonNode defs = generatedSchemaForClass.get("$defs");
        if (defs != null && defs.isObject()) {
            // If the generated schema has $defs, it's a complex type, use $ref
            ObjectNode refNode = objectMapper.createObjectNode();
            refNode.put("$ref", "#/$defs/" + clazz.getSimpleName()); // Use simple name for $ref
            propertiesNode.set(parameterName, refNode);
            // Copy the $defs from the generated schema for clazz to the new root schema
            rootSchema.set("$defs", defs);
        } else {
            // If no $defs, it's a simple type, embed its schema directly
            propertiesNode.set(parameterName, generatedSchemaForClass);
        }

        // Add "required" array for the parameter
        com.fasterxml.jackson.databind.node.ArrayNode requiredNode = objectMapper.createArrayNode();
        requiredNode.add(parameterName);
        rootSchema.set("required", requiredNode);

        try {
            return objectMapper.writeValueAsString(rootSchema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Try to parse a JSON for better MCP tool output withot class specification
     * If parsing fails it will return a object like this { response: <raw arguent> }
     * @param jsonRaw
     * @return
     */
    public static JsonNode parseJson(String jsonRaw) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonRaw);
        } catch (JsonProcessingException e) {
            return objectMapper.createObjectNode().put("response", jsonRaw);
        }
    }
}

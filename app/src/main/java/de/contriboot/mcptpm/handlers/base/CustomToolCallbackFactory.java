package de.contriboot.mcptpm.handlers;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.metadata.ToolMetadata;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CustomToolCallbackFactory {

    /**
     * Creates an array of ToolCallback instances from the methods of the given
     * bean that are annotated with @ToolCustomSchema.
     *
     * @param bean The object instance containing the tool methods.
     * @return An array of ToolCallback objects.
     */
    public static ToolCallback[] from(Object bean) {
        List<ToolCallback> toolCallbacks = new ArrayList<>();
        for (Method method : ReflectionUtils.getAllDeclaredMethods(bean.getClass())) {
            if (method.isAnnotationPresent(ToolCustomSchema.class)) {
                ToolCustomSchema annotation =
                        method.getAnnotation(ToolCustomSchema.class);

                // 1. Build the ToolDefinition using the annotation values
                ToolDefinition toolDefinition = ToolDefinition.builder()
                        .name(
                                StringUtils.hasText(annotation.name())
                                        ? annotation.name()
                                        : method.getName()
                        )
                        .description(annotation.description())
                        .inputSchema(annotation.schema()) // Use the custom schema!
                        .build();

                // 2. Build the ToolMetadata
                ToolMetadata toolMetadata =
                        ToolMetadata.builder().returnDirect(annotation.returnDirect()).build();

                // 3. Create the MethodToolCallback
                MethodToolCallback toolCallback = MethodToolCallback.builder()
                        .toolDefinition(toolDefinition)
                        .toolMetadata(toolMetadata)
                        .toolMethod(method)
                        .toolObject(bean)
                        .build();

                toolCallbacks.add(toolCallback);
            }
        }
        return toolCallbacks.toArray(new ToolCallback[0]);
    }

}
package ai.mem0.sdk.internal;

import ai.mem0.sdk.filter.MemoryFilter;
import ai.mem0.sdk.filter.MemoryFilterSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.module.SimpleModule;

public final class Mem0ObjectMapperFactory {

    private Mem0ObjectMapperFactory() {}

    public static ObjectMapper createDefault() {
        return configure(new ObjectMapper());
    }
    public static ObjectMapper configure(ObjectMapper objectMapper) {
        return objectMapper.rebuild()
                .addModule(new SimpleModule().addSerializer(MemoryFilter.class, new MemoryFilterSerializer()))
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .changeDefaultPropertyInclusion(current -> JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL))
                .build();
    }
}


package ai.mem0.sdk.filter;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public final class MemoryFilterSerializer extends ValueSerializer<MemoryFilter> {
    @Override
    public void serialize(MemoryFilter value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {

    }
}

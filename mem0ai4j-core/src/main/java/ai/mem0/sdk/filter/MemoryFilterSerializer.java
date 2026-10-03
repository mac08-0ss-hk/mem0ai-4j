package ai.mem0.sdk.filter;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.Map;

public final class MemoryFilterSerializer extends ValueSerializer<MemoryFilter> {
    @Override
    public void serialize(MemoryFilter value, JsonGenerator generator, SerializationContext serializers)
            throws JacksonException {
        writeFilter(generator,value);
    }

    private void writeFilter(JsonGenerator generator, MemoryFilter filter) throws JacksonException {
        if (filter instanceof MemoryFilter.Field field) {
            generator.writeStartObject();
            generator.writePOJOProperty(field.field(), field.value());
            generator.writeEndObject();
            return;
        }

        if(filter instanceof MemoryFilter.Comparison comparison){
            generator.writeStartObject();
            generator.writeObjectPropertyStart(comparison.field());
            generator.writePOJOProperty(comparison.operator(), comparison.value());
            generator.writeEndObject();
            generator.writeEndObject();
            return;
        }

        if(filter instanceof MemoryFilter.Logical logical){
            generator.writeStartObject();
            generator.writeArrayPropertyStart(logical.operator());
            for(MemoryFilter child : logical.filters()){
                writeFilter(generator, child);
            }
            generator.writeEndArray();
            generator.writeEndObject();
            return;
        }

        if(filter instanceof MemoryFilter.Raw raw){
            generator.writeStartObject();
            for(Map.Entry<String, Object> entry : raw.values().entrySet()){
                generator.writePOJOProperty(entry.getKey(), entry.getValue());
            }
            generator.writeEndObject();
            return;
        }

        throw new IllegalArgumentException("Unsupported filter type: " + filter.getClass().getName());



    }
}

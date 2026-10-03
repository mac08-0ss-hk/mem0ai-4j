package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BatchMemoryUpdateItem(String memoryId,
                                    String text,
                                    Map<String, Object> metadata) {

    public BatchMemoryUpdateItem {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String memoryId;
        private String text;
        private Map<String, Object> metadata = Map.of();

        public Builder memoryId(String memoryId) {
        this.memoryId = memoryId;
        return this;
        }
        public Builder text(String text) {
            this.text = text;
            return this;
        }
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }
        public BatchMemoryUpdateItem build() {
            return new BatchMemoryUpdateItem(memoryId, text, metadata);
        }
    }
}


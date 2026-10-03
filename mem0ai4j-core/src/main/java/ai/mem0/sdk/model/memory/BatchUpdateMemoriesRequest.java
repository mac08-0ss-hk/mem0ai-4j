package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BatchUpdateMemoriesRequest(List<BatchMemoryUpdateItem>  memories) {

    public BatchUpdateMemoriesRequest {
        memories = memories == null ? List.of() : List.copyOf(memories);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<BatchMemoryUpdateItem> memories = List.of();

        public Builder memories(List<BatchMemoryUpdateItem> memories) {
            this.memories = memories;
            return this;
        }

        public BatchUpdateMemoriesRequest build() {
            return new BatchUpdateMemoriesRequest(memories);
        }
    }
}

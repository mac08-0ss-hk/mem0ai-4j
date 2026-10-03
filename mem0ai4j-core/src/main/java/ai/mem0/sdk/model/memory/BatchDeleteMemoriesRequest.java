package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BatchDeleteMemoriesRequest(List<BatchMemoryDeleteItem> memories) {

    public BatchDeleteMemoriesRequest {
        memories = memories == null ? List.of() : List.copyOf(memories);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private List<BatchMemoryDeleteItem> memories = List.of();

        public Builder memories(List<BatchMemoryDeleteItem> memories) {
            this.memories = memories;
            return this;
        }

        public BatchDeleteMemoriesRequest build() {
            return new BatchDeleteMemoriesRequest(memories);
        }
    }
}

package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BatchMemoryDeleteItem(String memoryId) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String memoryId;

        public Builder memoryId(String memoryId) {
            this.memoryId = memoryId;
            return this;
        }

        public BatchMemoryDeleteItem build() {
            return new BatchMemoryDeleteItem(memoryId);
        }
    }
}

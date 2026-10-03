package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateMemoryRequest(String text,
                                  Map<String, Object> metadata,
                                  Object timestamp,
                                  String expirationDate) {

    public UpdateMemoryRequest {
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String text;
        private Map<String, Object> metadata = Map.of();
        private Object timestamp;
        private String expirationDate;

        public Builder text(String text) {
            this.text = text;
            return this;
        }
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }
        public Builder timestamp(Object timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        public Builder expirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public UpdateMemoryRequest build() {
            return new UpdateMemoryRequest(text, metadata, timestamp, expirationDate);
        }







    }
}

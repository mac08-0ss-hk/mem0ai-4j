package ai.mem0.sdk.model.profile;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateProfileSettingsRequest(Boolean enabled,
                                           Map<String, Object> schema, String customInstructions) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean enabled;
        private Map<String, Object> schema;
        private String customInstructions;

        private Builder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        public Builder schema(Map<String, Object> schema) {
            this.schema = schema;
            return this;
        }
        public Builder customInstructions(String customInstructions) {
            this.customInstructions = customInstructions;
            return this;
        }
        public UpdateProfileSettingsRequest build() {
            return new UpdateProfileSettingsRequest(enabled, schema, customInstructions);
        }
    }
}

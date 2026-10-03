package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddMemoryRequest(
        List<MemoryMessage> messages,
        String userId,
        String agentId,
        String runId,
        String appId,
        Map<String, Object> metadata,
        Boolean infer,
        String expirationDate,
        List<Map<String, String>> customCategories,
        String customInstructions,
        String agentCustomInstructions,
        Boolean immutable,
        String includes,
        String excludes,
        Boolean enableGraph,
        Boolean temporalReasoning

) {

    public AddMemoryRequest {
        messages = messages == null ? List.of() : List.copyOf(messages);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
        customCategories = customCategories == null ? List.of() : List.copyOf(customCategories);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {

        private List<MemoryMessage> messages = List.of();
        private String userId;
        private String agentId;
        private String runId;
        private String appId;
        private Map<String, Object> metadata = Map.of();
        private Boolean infer;
        private String expirationDate;
        private List<Map<String, String>> customCategories = List.of();
        private String customInstructions;
        private String agentCustomInstructions;
        private Boolean immutable;
        private String includes;
        private String excludes;
        private Boolean enableGraph;
        private Boolean temporalReasoning;

        public Builder messages(List<MemoryMessage> messages){
            this.messages = messages;
            return this;
        }

        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }

        public Builder agentId(String agentId){
            this.agentId = agentId;
            return this;
        }

        public Builder runId(String runId){
            this.runId = runId;
            return this;
        }

        public Builder appId(String appId){
            this.appId = appId;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata){
            this.metadata = metadata;
            return this;
        }

        public Builder infer(Boolean infer){
            this.infer = infer;
            return this;
        }
        public Builder expirationDate(String expirationDate){
            this.expirationDate = expirationDate;
            return this;
        }
        public Builder customCategories(List<Map<String, String>> customCategories){
            this.customCategories = customCategories;
            return this;
        }
        public Builder customInstructions(String customInstructions){
            this.customInstructions = customInstructions;
            return this;
        }
        public Builder agentCustomInstructions(String agentCustomInstructions){
            this.agentCustomInstructions = agentCustomInstructions;
            return this;
        }
        public Builder immutable(Boolean immutable){
            this.immutable = immutable;
            return this;
        }
        public Builder includes(String includes){
            this.includes = includes;
            return this;
        }
        public Builder excludes(String excludes){
            this.excludes = excludes;
            return this;
        }
        public Builder enableGraph(Boolean enableGraph){
            this.enableGraph = enableGraph;
            return this;
        }
        public Builder temporalReasoning(Boolean temporalReasoning){
            this.temporalReasoning = temporalReasoning;
            return this;
        }
        public AddMemoryRequest build(){
            return new AddMemoryRequest(messages, userId, agentId, runId, appId, metadata,
                    infer, expirationDate, customCategories, customInstructions,
                    agentCustomInstructions, immutable, includes, excludes, enableGraph, temporalReasoning);
        }

    }

}

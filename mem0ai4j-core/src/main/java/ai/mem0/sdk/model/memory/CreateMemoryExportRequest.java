package ai.mem0.sdk.model.memory;

import ai.mem0.sdk.filter.MemoryFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateMemoryExportRequest(Object schema, MemoryFilter filters,
                                        String exportInstructions,
                                        String orgId, String projectId) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Object schema;
        private MemoryFilter filters;
        private String exportInstructions;
        private String orgId;
        private String projectId;

        public Builder schema(Object schema) {
            this.schema = schema;
            return this;
        }

        public Builder filters(MemoryFilter filters) {
            this.filters = filters;
            return this;
        }

        public Builder exportInstructions(String exportInstructions) {
            this.exportInstructions = exportInstructions;
            return this;
        }

        public Builder orgId(String orgId) {
            this.orgId = orgId;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public CreateMemoryExportRequest build() {
            return new CreateMemoryExportRequest(schema, filters, exportInstructions, orgId, projectId);
        }


    }


}

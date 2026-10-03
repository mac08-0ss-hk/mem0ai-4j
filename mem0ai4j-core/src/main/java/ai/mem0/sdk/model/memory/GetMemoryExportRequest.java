package ai.mem0.sdk.model.memory;

import ai.mem0.sdk.filter.MemoryFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetMemoryExportRequest(String memoryExportId, MemoryFilter filters) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String memoryExportId;
        private MemoryFilter filters;

        public Builder memoryExportId(String memoryExportId) {
            this.memoryExportId = memoryExportId;
            return this;
        }

        public Builder filters(MemoryFilter filters) {
            this.filters = filters;
            return this;
        }

        public GetMemoryExportRequest build() {
            return new GetMemoryExportRequest(memoryExportId, filters);
        }
    }

}

package ai.mem0.sdk.model.memory;

import ai.mem0.sdk.filter.MemoryFilter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SearchMemoryRequest(
        String query,
        MemoryFilter filters,
        Boolean showExpired,
        Integer topK,
        Double threshold,
        Boolean rerank,
        String referenceDate,
        List<String> fields,
        List<String> categories,
        Map<String, Object> metadata
) {

    public SearchMemoryRequest {
        fields = fields == null ? List.of() : List.copyOf(fields);
        categories = categories == null ? List.of() : List.copyOf(categories);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String query;
        private MemoryFilter filters;
        private Boolean showExpired;
        private Integer topK;
        private Double threshold;
        private Boolean rerank;
        private String referenceDate;
        private List<String> fields = Collections.emptyList();
        private List<String> categories = Collections.emptyList();
        private Map<String, Object> metadata = Collections.emptyMap();

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder filters(MemoryFilter filters) {
            this.filters = filters;
            return this;
        }

        public Builder showExpired(Boolean showExpired) {
            this.showExpired = showExpired;
            return this;
        }

        public Builder topK(Integer topK) {
            this.topK = topK;
            return this;
        }

        public Builder threshold(Double threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder rerank(Boolean rerank) {
            this.rerank = rerank;
            return this;
        }

        public Builder referenceDate(String referenceDate) {
            this.referenceDate = referenceDate;
            return this;
        }

        public Builder fields(List<String> fields) {
            this.fields = fields == null ? List.of() : List.copyOf(fields);
            return this;
        }

        public Builder categories(List<String> categories) {
            this.categories = categories == null ? List.of() : List.copyOf(categories);
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
            return this;
        }

        public SearchMemoryRequest build() {
            return new SearchMemoryRequest(query, filters, showExpired, topK, threshold, rerank, referenceDate, fields, categories, metadata);
        }
    }









}

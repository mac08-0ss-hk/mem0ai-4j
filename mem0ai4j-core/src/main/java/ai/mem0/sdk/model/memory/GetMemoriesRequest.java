package ai.mem0.sdk.model.memory;

import ai.mem0.sdk.filter.MemoryFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetMemoriesRequest(
        MemoryFilter filters,
        Boolean showExpired,
        String startDate,
        String endDate,
        List<String> categories,
        List<String> fields,
        String keywords,
        Integer page,
        Integer pageSize,
        Map<String, Object> metadata
) {

    public GetMemoriesRequest {
        categories = categories == null ? List.of() : List.copyOf(categories);
        fields = fields == null ? List.of() : List.copyOf(fields);
        metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private MemoryFilter filters;
        private Boolean showExpired;
        private String startDate;
        private String endDate;
        private List<String> categories = List.of();
        private List<String> fields = List.of();
        private String keywords;
        private Integer page;
        private Integer pageSize;
        private Map<String, Object> metadata = Map.of();

        public Builder filter(MemoryFilter filters) {
            this.filters = filters;
            return this;
        }

        public Builder showExpired(Boolean showExpired) {
            this.showExpired = showExpired;
            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public Builder fields(List<String> fields) {
            this.fields = fields;
            return this;
        }

        public Builder keywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public GetMemoriesRequest build() {
            return new GetMemoriesRequest(
                    filters,
                    showExpired,
                    startDate,
                    endDate,
                    categories,
                    fields,
                    keywords,
                    page,
                    pageSize,
                    metadata
            );
        }




    }




}

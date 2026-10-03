package ai.mem0.sdk.langchain4j.longtermmemory;

import ai.mem0.sdk.client.Mem0Client;
import ai.mem0.sdk.filter.MemoryFilter;
import ai.mem0.sdk.model.memory.MemoryRecord;
import ai.mem0.sdk.model.memory.SearchMemoryRequest;
import java.util.List;
import java.util.Objects;

public final class Mem0LongTermMemory {

    private final Mem0Client client;
    private final String appId;
    private final boolean scopeToMemoryId;
    private final int topK;
    private final Double threshold;

    public Mem0LongTermMemory(Mem0Client client, String appId, boolean scopeToMemoryId, int topK, Double threshold) {
        this.client = client;
        this.appId = appId;
        this.scopeToMemoryId = scopeToMemoryId;
        this.topK = topK;
        this.threshold = threshold;
    }

    public static Builder builder(){
        return new Builder();
    }

    public List<MemoryRecord> search(Object memoryId, String query){
        Objects.requireNonNull(memoryId,"memoryId must not be null");
        Objects.requireNonNull(query,"query must not be null");
        return client.search(SearchMemoryRequest.builder()
                .query(query)
                .filters(filterFor(memoryId))
                .topK(topK)
                .threshold(threshold)
                .build())
                .results();
    }

    private MemoryFilter filterFor(Object memoryId) {
        if(!scopeToMemoryId){
            return MemoryFilter.eq("app_id", appId);
        }
        return MemoryFilter.eq("user_id", String.valueOf(memoryId));
    }


    public String memoryContext(Object memoryId, String query){
        Objects.requireNonNull(memoryId,"memoryId must not be null");
        Objects.requireNonNull(query,"query must not be null");
        List<MemoryRecord> records = search(memoryId, query);
        if(records.isEmpty()){
            return "";
        }
        return records.stream()
                .map(MemoryRecord::memory)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> "-" + text)
                .reduce((left, right) -> left + "\n" + right)
                .orElse("");
    }

    public static final class Builder {
        private Mem0Client client;
        private String appId ="langchain4j";
        private boolean scopeToMemoryId;
        private int topK = 5;
        private Double threshold;

        public Builder client(Mem0Client client) {
            this.client = client;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder scopeToMemoryId(boolean scopeToMemoryId) {
            this.scopeToMemoryId = scopeToMemoryId;
            return this;
        }

        public Builder topK(int topK) {
            this.topK = topK;
            return this;
        }

        public Builder threshold(Double threshold) {
            this.threshold = threshold;
            return this;
        }

        public Mem0LongTermMemory build() {
            Objects.requireNonNull(client, "client is required");
            if(appId == null || appId.isBlank()) {
                throw new IllegalArgumentException("appId is required");
            }
            if(topK <= 0) {
                throw new IllegalArgumentException("topK is required");
            }
            return new Mem0LongTermMemory(client, appId, scopeToMemoryId, topK, threshold);
        }
    }

}

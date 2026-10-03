package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record MemoryHistoryRecord(
        String id,
        String memoryId,
        List<MemoryMessage> input,
        String oldMemory,
        String newMemory,
        String userId,
        List<String> categories,
        String event,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}

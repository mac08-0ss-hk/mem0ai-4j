package ai.mem0.sdk.model.memory;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemoryRecord(
        String id,
        String memory,
        String userId,
        String agentId,
        String appId,
        String sessionId,
        Map<String, Object> metadata,
        List<String> categories,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        String expirationDate,
        Map<String, Object> structuredAttributes,
        String replacedBy,
        Boolean synthesized,
        String lifecycleState
) {
}

package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddMemoryResponse(String eventId, String status,
                                String message, List<MemoryRecord> results) {
}

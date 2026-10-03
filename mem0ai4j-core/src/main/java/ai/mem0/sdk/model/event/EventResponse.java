package ai.mem0.sdk.model.event;

import ai.mem0.sdk.model.memory.MemoryRecord;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record EventResponse(String eventId, String status, String message,
                            List<MemoryRecord> results) {
}

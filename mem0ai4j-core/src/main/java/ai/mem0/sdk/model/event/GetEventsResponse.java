package ai.mem0.sdk.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record GetEventsResponse(long count,
                                List<EventRecord> results,
                                String next,
                                String previous) {
}

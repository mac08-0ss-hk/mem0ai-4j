package ai.mem0.sdk.client;

import ai.mem0.sdk.model.event.EventRecord;
import ai.mem0.sdk.model.event.GetEventsResponse;

public interface Mem0EventClient {

    GetEventsResponse list(Integer page, Integer pageSize);
    EventRecord get(String eventId);
}

package ai.mem0.sdk.client;

import ai.mem0.sdk.model.BatchOperationResponse;
import ai.mem0.sdk.model.event.EventResponse;
import ai.mem0.sdk.model.memory.*;
import com.sun.jdi.request.EventRequest;

import java.util.List;
import java.util.Map;

public interface Mem0Client {

    AddMemoryResponse add(AddMemoryRequest request);
    SearchMemoryResponse search(SearchMemoryRequest request);
    GetMemoriesResponse get(GetMemoriesRequest request);
    MemoryRecord get(String memoryId);
    List<MemoryHistoryRecord> history(String memoryId);
    MemoryRecord update(String memoryId, UpdateMemoryRequest request);
    DeleteMemoryResponse delete(String memoryId, boolean deleteLinked);
    DeleteMemoriesResponse deleteAll(String userId, String agentId, String appId, String runId, String orgId, String projectId);
    BatchOperationResponse batchUpdate(BatchUpdateMemoriesRequest request);
    BatchOperationResponse batchDelete(BatchDeleteMemoriesRequest request);
    CreateMemoryExportResponse createMemoryExport(CreateMemoryExportRequest request);
    Map<String, Object> getMemoryExport(GetMemoryExportRequest request);
    FeedbackResponse  feedback(FeedbackRequest request);
    EventResponse event(String eventId);

    Mem0EventClient events();
    Mem0EntityClient entities();
    Mem0OrganizationClient organizations();
    Mem0WebhookClient webhooks();
    Mem0ProjectClient projects();
    Mem0ProfileClient profiles();
    Mem0DreamClient dreams();

}

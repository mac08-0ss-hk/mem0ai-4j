package ai.mem0.sdk.client.impl;

import ai.mem0.sdk.client.*;
import ai.mem0.sdk.filter.MemoryFilter;
import ai.mem0.sdk.internal.Mem0ApiProfile;
import ai.mem0.sdk.internal.Mem0ClientConfiguration;
import ai.mem0.sdk.internal.Mem0RequestExecutor;
import ai.mem0.sdk.internal.Mem0RequestValidator;
import ai.mem0.sdk.model.BatchOperationResponse;
import ai.mem0.sdk.model.event.EventResponse;
import ai.mem0.sdk.model.memory.*;

import java.util.*;

public class DefaultMem0Client implements Mem0Client {

    private final Mem0ApiProfile apiProfile;
    private final Mem0RequestExecutor executor;

    public DefaultMem0Client(Mem0ClientConfiguration configuration, Mem0RequestExecutor executor) {
        this.apiProfile = Objects.requireNonNull(configuration, "configurtion").apiProfile();
        this.executor = Objects.requireNonNull(executor, "executor");
    }


    @Override
    public AddMemoryResponse add(AddMemoryRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.post("/memories", selfHostedAddPayload(request), AddMemoryResponse.class);
        }
        return executor.post("/v3/memories", request, AddMemoryResponse.class);
    }


    @Override
    public SearchMemoryResponse search(SearchMemoryRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.post("/search", selfHostedSearchPayload(request), SearchMemoryResponse.class);
        }
        return executor.post("/v3/memories", request, SearchMemoryResponse.class);
    }

    @Override
    public GetMemoriesResponse get(GetMemoriesRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.get(selfHostedGetPath(request), GetMemoriesResponse.class);
        }
        Integer page = request.page() == null ? null : request.page();
        Integer pageSize = request.pageSize() == null ? 100 : request.pageSize();
        String path = "/v3/memories/?page=" + page + "&page_size=" + pageSize;
        return executor.post(path, request, GetMemoriesResponse.class);
    }

    @Override
    public MemoryRecord get(String memoryId) {
        Objects.requireNonNull(memoryId, "memoryId");
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.get("/memories/" + memoryId, MemoryRecord.class);
        }
        return executor.get("/v1/memories/" + memoryId + "/" , MemoryRecord.class);
    }

    @Override
    public List<MemoryHistoryRecord> history(String memoryId) {
        Objects.requireNonNull(memoryId, "memoryId");
        String path = apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER
                ? "/memories/" + memoryId + "/history"
                : "/v1/memories/" + memoryId + "/history";
        MemoryHistoryRecord[] records = executor.get(path, MemoryHistoryRecord[].class);
        return records == null ? List.of() : List.of(records);
    }

    @Override
    public MemoryRecord update(String memoryId, UpdateMemoryRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.patch("/memories/" + memoryId, request, MemoryRecord.class);
        }
        return executor.patch("/v1/memories/" + memoryId + "/", request, MemoryRecord.class);
    }

    @Override
    public DeleteMemoryResponse delete(String memoryId, boolean deleteLinked) {
        if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return executor.delete("/memories/" + memoryId , DeleteMemoryResponse.class);
        }
        String path = deleteLinked ? "/v1/memories/" + memoryId +
                "/?delete_linked=true" : "/v1/memories" + memoryId + "/";
        return executor.delete(path, DeleteMemoryResponse.class);
    }

    @Override
    public DeleteMemoriesResponse deleteAll(String userId, String agentId, String appId, String runId, String orgId, String projectId) {
       Mem0RequestValidator.validateDeleteAll(userId, agentId, appId, runId, orgId, projectId);
       if (apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
           StringBuilder selfHostedPath = new StringBuilder("/memories");
           boolean hasQuery = false;
           hasQuery = appendQuery(selfHostedPath, hasQuery, "user_id", userId == null || userId.isBlank() ? appId : userId);
           hasQuery = appendQuery(selfHostedPath, hasQuery, "agent_id", agentId);
           appendQuery(selfHostedPath, hasQuery, "run_id", runId);
           return executor.delete(selfHostedPath.toString(), DeleteMemoriesResponse.class);
       }
        StringBuilder path = new StringBuilder("/v1/memories");
        boolean hasQuery = false;
        hasQuery = appendQuery(path, hasQuery, "user_id", userId);
        hasQuery = appendQuery(path, hasQuery, "agent_id", agentId);
        hasQuery = appendQuery(path, hasQuery, "app_id", appId);
        hasQuery = appendQuery(path, hasQuery, "run_id", runId);
        hasQuery = appendQuery(path, hasQuery, "org_id", orgId);
        appendQuery(path, hasQuery, "project_id", projectId);
        return executor.delete(path.toString(), DeleteMemoriesResponse.class);
    }

    @Override
    public BatchOperationResponse batchUpdate(BatchUpdateMemoriesRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        return executor.put("/v1/batch/", request, BatchOperationResponse.class);
    }

    @Override
    public BatchOperationResponse batchDelete(BatchDeleteMemoriesRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        return executor.delete("/v1/batch/", request, BatchOperationResponse.class);
    }

    @Override
    public CreateMemoryExportResponse createMemoryExport(CreateMemoryExportRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        return executor.post("/v1/exports/", request, CreateMemoryExportResponse.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getMemoryExport(GetMemoryExportRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        return executor.post("/v1/exports/get", request, Map.class);
    }

    @Override
    public FeedbackResponse feedback(FeedbackRequest request) {
        Objects.requireNonNull(request, "request");
        Mem0RequestValidator.validate(request);
        return executor.post("/v1/feedback/", request, FeedbackResponse.class);
    }

    @Override
    public EventResponse event(String eventId) {
        return executor.get("/v1/events/" + eventId + "/", EventResponse.class);
    }

    @Override
    public Mem0EventClient events() {
        return null;
    }

    @Override
    public Mem0EntityClient entities() {
        return null;
    }

    @Override
    public Mem0OrganizationClient organizations() {
        return null;
    }

    @Override
    public Mem0WebhookClient webhooks() {
        return null;
    }

    @Override
    public Mem0ProjectClient projects() {
        return null;
    }

    @Override
    public Mem0ProfileClient profiles() {
        return null;
    }

    @Override
    public Mem0DreamClient dreams() {
        return null;
    }


    private static Map<String, Object> selfHostedAddPayload(AddMemoryRequest request) {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("messages", request.messages());
        putIfPresent(payload, "userId", primaryIdentifier(request.userId(), request.appId()));
        putIfPresent(payload, "agent_id", request.agentId());
        putIfPresent(payload, "run_id", request.runId());
        putIfPresent(payload, "metadata", request.metadata().isEmpty() ? null : request.metadata());
        putIfPresent(payload, "infer", request.infer());
        putIfPresent(payload, "expiration_date", request.expirationDate());
        putIfPresent(payload, "custom_categories", request.customCategories());
        putIfPresent(payload, "custom_instructions", request.customInstructions());
        putIfPresent(payload, "agent_custom_instructions", request.agentCustomInstructions());
        putIfPresent(payload, "immutable", request.immutable());
        putIfPresent(payload, "includes", request.includes());
        putIfPresent(payload, "excludes", request.excludes());
        putIfPresent(payload, "enable_graph", request.enableGraph());
        putIfPresent(payload, "temporal_reasoning", request.temporalReasoning());
        return payload;
    }

    private static Map<String, Object> selfHostedSearchPayload(SearchMemoryRequest request) {
        Map<String, Object> payload = new LinkedHashMap<>();
        Identifiers identifiers = identifiersFromFilter(request.filters());
        payload.put("query", request.query());
        putIfPresent(payload, "userId", identifiers.userId());
        putIfPresent(payload, "agent_id", identifiers.agentId());
        putIfPresent(payload, "run_id", identifiers.runId());
        putIfPresent(payload, "filters", request.filters());
        putIfPresent(payload, "limit", request.topK());
        putIfPresent(payload, "threshold", request.threshold());
        return payload;
    }

    private static String selfHostedGetPath(GetMemoriesRequest request) {
        Identifiers identifiers = identifiersFromFilter(request.filters());
        StringBuilder path = new StringBuilder("/memories");
        boolean hasQuery = false;
        hasQuery = appendQuery(path, hasQuery, "user_id", identifiers.userId);
        hasQuery = appendQuery(path, hasQuery, "agent_id", identifiers.agentId);
        appendQuery(path, hasQuery, "run_id", identifiers.runId);
        return path.toString();

    }

    private static boolean appendQuery(StringBuilder path, boolean hasQuery, String name, String value) {
        if(value == null || value.isBlank()){
            return hasQuery;
        }
        path.append(hasQuery ? "&" : "?").append(name).append("=").append(value);
        return true;
    }

    private static Identifiers identifiersFromFilter(MemoryFilter filter) {
        Identifiers identifiers = new Identifiers(null, null, null, null);
        if (filter == null) {
            return identifiers;
        }
        return switch (filter) {
            case MemoryFilter.Field field -> identifiers.with(field.field(), field.value());
            case MemoryFilter.Raw raw -> raw.values().entrySet().stream()
                    .reduce(identifiers, (current, entry) -> current.with(entry.getKey(), entry.getValue()), (left, right) -> right);
            case MemoryFilter.Logical logical -> logical.filters().stream()
                    .reduce(identifiers, (current, nestedFilter) -> current.merge(identifiersFromFilter(nestedFilter)),Identifiers::merge);
            case MemoryFilter.Comparison comparison -> identifiers.with(comparison.field(), comparison.value());
        };
    }

    private  record Identifiers(String userId, String agentId, String runId, String appId) {

        private Identifiers with(String field, Object value) {
            if(!(value instanceof String stringValue) || stringValue.isBlank()) {
                return this;
            }
            return switch(field){
                case "user_id" -> new Identifiers(stringValue,agentId,runId,appId);
                case "agent_id" -> new Identifiers(userId,stringValue,runId,appId);
                case "run_id" -> new Identifiers(userId,agentId,stringValue,appId);
                case "app_id" -> new Identifiers(userId == null || userId.isBlank() ?
                        stringValue:userId, agentId, runId, stringValue);
                default -> this;
            };
        }

        private Identifiers merge(Identifiers other) {
            return new Identifiers(
                    userId !=null ? userId : other.userId,
                    agentId != null ? agentId : other.agentId,
                    runId  != null ? runId : other.runId,
                    appId != null ? appId : other.appId
            );
        }
    }

    private static String primaryIdentifier(String userId, String appId) {
        return userId == null || userId.isBlank() ? appId : userId;
    }


    private static void putIfPresent(Map<String, Object> payload, String key, Object value) {
        if (value != null) {
            payload.put(key, value);
        }
    }
}

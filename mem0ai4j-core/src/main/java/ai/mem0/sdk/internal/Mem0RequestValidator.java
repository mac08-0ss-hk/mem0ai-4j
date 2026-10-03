package ai.mem0.sdk.internal;

import ai.mem0.sdk.exception.Mem0ConfigurationException;
import ai.mem0.sdk.filter.MemoryFilter;
import ai.mem0.sdk.model.memory.*;
import ai.mem0.sdk.model.profile.UpdateProfileSettingsRequest;

public final class Mem0RequestValidator {


    private Mem0RequestValidator() {}

    public static void validate(AddMemoryRequest request){
        requireAtLeastOneEntityId(request.userId(), request.agentId(), request.runId(), request.appId());
        if(request.messages() == null || request.messages().isEmpty()){
            throw new Mem0ConfigurationException("At least one message must be provided.");
        }
    }

    public static void validate(SearchMemoryRequest request){
        requireQuery(request.query());
        requireAtLeastOneEntityId(request.filters());
    }

    public static void validate(GetMemoriesRequest  request){
        requireAtLeastOneEntityId(request.filters());
    }

    public static void validate(CreateMemoryExportRequest  request){
        if(request.schema() == null){
            throw new Mem0ConfigurationException("At least one schema must be provided.Schema must not be null.");
        }
        if(request.filters() == null){
            throw new Mem0ConfigurationException("At least one filters must be provided.Filters must not be null.");
        }
    }

    public static void validate(GetMemoryExportRequest   request){
        if(isBlank(request.memoryExportId()) && request.filters() == null){
            throw new Mem0ConfigurationException("At least one memory export must be provided.MemoryExportId must be provided.");
        }
    }

    public static void validate(FeedbackRequest  request){
        if(isBlank(request.memoryId())){
            throw new Mem0ConfigurationException("MemoryId must be provided.");
        }
       if(isBlank(request.feedback())){
           throw new Mem0ConfigurationException("Feedback must be provided.");
       }
    }

    public static void validateDeleteAll(String userId, String agentId, String runId, String appId, String orgId, String projectId){
        if(isBlank(userId) && isBlank(agentId) && isBlank(runId) && isBlank(appId) && isBlank(orgId) && isBlank(projectId)){
            throw new Mem0ConfigurationException("At least one of userId, agentId, runId, appId, orgId or projectId must be provided.");
        }
    }

    public static void validate(UpdateMemoryRequest  request){
        if(request.text() == null && (request.metadata() == null || request.metadata().isEmpty())
        && request.timestamp() == null && request.expirationDate() == null){
            throw new Mem0ConfigurationException("At least one of text or metadata must be provided.");
        }
    }

    public static void validate(BatchUpdateMemoriesRequest  request){
        if(request.memories() == null || request.memories().isEmpty()){
            throw new Mem0ConfigurationException("At least one memory must be provided.");
        }
    }

    public static void validate(BatchDeleteMemoriesRequest  request){
        if(request.memories() == null || request.memories().isEmpty()){
            throw new Mem0ConfigurationException("At least one memory must be provided.");
        }
    }

    public static void validate(UpdateProfileSettingsRequest request){
        if(request.enabled() == null && request.schema() == null && isBlank(request.customInstructions())){
            throw new Mem0ConfigurationException("At least one of enabled, schema or customInstructions must be provided.");
        }
    }

    private static void requireQuery(String query) {
        if(isBlank(query)){
            throw new Mem0ConfigurationException("At least one query must be provided.");
        }
    }


    private static void requireAtLeastOneEntityId(String userId,String agentId,
                                                 String runId, String appId){
        if(isBlank(userId) && isBlank(agentId) && isBlank(runId) && isBlank(appId)){
            throw new Mem0ConfigurationException("At least one of userId, agentId, runId or appId must be provided.");
        }
    }

    private static void requireAtLeastOneEntityId(MemoryFilter  filter){
        if(filter == null){
            throw new Mem0ConfigurationException("At least one of filter must be provided.");
        }
    }

    private static boolean isBlank(String value){
        return value == null || value.isBlank();
    }


}

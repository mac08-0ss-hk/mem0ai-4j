package ai.mem0.sdk.langchain4j.tool;

import ai.mem0.sdk.langchain4j.longtermmemory.Mem0LongTermMemory;
import dev.langchain4j.agent.tool.Tool;

import java.util.Objects;

public final class Mem0MemoryTool {

    private final Mem0LongTermMemory mem0LongTermMemory;

    public Mem0MemoryTool(Mem0LongTermMemory mem0LongTermMemory) {
        this.mem0LongTermMemory = Objects.requireNonNull(mem0LongTermMemory);
    }


    @Tool("Search long-term memory for durable user facts, preferences, and prior context before answering personalization or follow-up questions.")
    public String searchLongTermMemory(String memoryId, String query){
        Objects.requireNonNull(memoryId);
        Objects.requireNonNull(query);
        return mem0LongTermMemory.memoryContext(memoryId, query);
    }
}

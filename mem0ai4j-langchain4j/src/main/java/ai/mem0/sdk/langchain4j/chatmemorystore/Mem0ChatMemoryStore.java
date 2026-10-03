package ai.mem0.sdk.langchain4j.chatmemorystore;

import ai.mem0.sdk.client.Mem0Client;
import ai.mem0.sdk.model.memory.MemoryMessage;
import dev.langchain4j.data.message.*;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class Mem0ChatMemoryStore implements ChatMemoryStore {

    private final Mem0Client client;
    private final String appId;
    private final Boolean infer;
    private final boolean scopeToMemoryId;
    private final boolean persistSystemMessages;
    private final BiFunction<Object, List<ChatMessage>, String> customInstructionsProvider;
    private final BiFunction<Object, List<ChatMessage>, String> agentCustomInstructionsProvider;
    private final BiFunction<Object, List<ChatMessage>, List<Map<String,String>>> customCategoriesProvider;
    private final Map<Object, List<ChatMessage>> snapshots;

    public Mem0ChatMemoryStore(Mem0Client client, String appId, Boolean infer, boolean scopeToMemoryId, boolean persistSystemMessages, BiFunction<Object, List<ChatMessage>, String> customInstructionsProvider, BiFunction<Object, List<ChatMessage>, String> agentCustomInstructionsProvider, BiFunction<Object, List<ChatMessage>, List<Map<String, String>>> customCategoriesProvider, Map<Object, List<ChatMessage>> snapshots) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.appId = appId;
        this.infer = infer;
        this.scopeToMemoryId = scopeToMemoryId;
        this.persistSystemMessages = persistSystemMessages;
        this.customInstructionsProvider = customInstructionsProvider;
        this.agentCustomInstructionsProvider = agentCustomInstructionsProvider;
        this.customCategoriesProvider = customCategoriesProvider;
        this.snapshots = snapshots;
    }


    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        return List.copyOf(snapshots.getOrDefault(memoryId, List.of()));
    }

    @Override
    public void updateMessages(Object o, List<ChatMessage> list) {

    }

    @Override
    public void deleteMessages(Object o) {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Mem0Client client;
        private String appId = "langchain4j";
        private Boolean infer = true;
        private boolean scopeToMemoryId;
        private boolean persistSystemMessages;
        private BiFunction<Object, List<ChatMessage>, String> customInstructionsProvider;
        private BiFunction<Object, List<ChatMessage>, String> agentCustomInstructionsProvider;
        private BiFunction<Object, List<ChatMessage>, List<Map<String,String>>> customCategoriesProvider;
        private Map<Object, List<ChatMessage>> snapshots = new ConcurrentHashMap<>();

        public Builder client(Mem0Client client) {
            this.client = client;
            return this;
        }
        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }
        public Builder infer(Boolean infer) {
            this.infer = infer;
            return this;
        }
        public Builder scopeToMemoryId(Boolean scopeToMemoryId) {
            this.scopeToMemoryId = scopeToMemoryId;
            return this;
        }

        public Builder persistSystemMessages(boolean persistSystemMessages) {
            this.persistSystemMessages = persistSystemMessages;
            return this;
        }

        public Builder customInstructionsProvider(BiFunction<Object, List<ChatMessage>, String> instructionsProvider) {
            this.customInstructionsProvider = instructionsProvider;
            return this;
        }

        public Builder agentCustomInstructionsProvider(BiFunction<Object, List<ChatMessage>, String> instructionsProvider) {
            this.agentCustomInstructionsProvider = instructionsProvider;
            return this;
        }

        public Builder customCategoriesProvider(BiFunction<Object, List<ChatMessage>, List<Map<String,String>>> categoriesProvider) {
            this.customCategoriesProvider = categoriesProvider;
            return this;
        }

        public Builder snapshots(Map<Object, List<ChatMessage>> snapshots) {
            this.snapshots = snapshots;
            return this;
        }

        public Mem0ChatMemoryStore build() {
            Objects.requireNonNull(client, "client must not be null");
            if(appId == null || appId.isBlank()) {
                throw new IllegalArgumentException("appId must not be null or blank");
            }
            return new Mem0ChatMemoryStore(client, appId, infer,
                    scopeToMemoryId, persistSystemMessages,
                    customInstructionsProvider, agentCustomInstructionsProvider,
                    customCategoriesProvider, snapshots);
        }
    }

    private static <T> T applyProvider(BiFunction<Object, List<ChatMessage>, T> provider,
             Object memoryId,List<ChatMessage> messages) {
        return provider == null ? null : provider.apply(memoryId, List.copyOf(messages));
    }

    private static void applyIfPresent(
            BiFunction<Object, List<ChatMessage>, String> provider,
            Object memoryId,
            List<ChatMessage> messages,
            Consumer<String> consumer) {

        String value = applyProvider(provider, memoryId, messages);
        if (value != null && !value.isBlank()) {
            consumer.accept(value);
        }
    }

    private List<MemoryMessage> toMemoryMessages(List<ChatMessage> messages) {
        return messages.stream()
                .filter(message -> persistSystemMessages || !(message instanceof SystemMessage))
                .map(Mem0ChatMemoryStore::toMemoryMessage)
                .toList();
    }

    private static MemoryMessage toMemoryMessage(ChatMessage chatMessage) {
        return switch (chatMessage) {
            case SystemMessage systemMessage -> MemoryMessage.system(systemMessage.text());
            case UserMessage userMessage -> MemoryMessage.user(userMessage.singleText());
            case AiMessage  aiMessage -> MemoryMessage.assistant(aiMessage.text());
            case ToolExecutionResultMessage  toolExecutionResultMessage -> MemoryMessage.tool(toolExecutionResultMessage.text());
            default -> MemoryMessage.system(chatMessage.toString());
        };
    }


}

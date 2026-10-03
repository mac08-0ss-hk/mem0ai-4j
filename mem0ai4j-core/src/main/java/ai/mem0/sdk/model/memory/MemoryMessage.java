package ai.mem0.sdk.model.memory;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemoryMessage(
        String role,
        Object content
) {
    public MemoryMessage {
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(content, "content");
    }

    public static MemoryMessage mediaUrl(String role, String type, String url) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(url, "url");
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put(type,Map.of("url", url));
        return new MemoryMessage(role, payload);
    }

    public static MemoryMessage structured(String role, Map<String, Object> content) {
        return new MemoryMessage(role, Map.copyOf(content));
    }

    public static MemoryMessage user(String content){
        return new MemoryMessage("user", content);
    }

    public static MemoryMessage assistant(String content){
        return new MemoryMessage("assistant", content);
    }

    public static MemoryMessage system(String content){
        return new MemoryMessage("system", content);
    }

    public static MemoryMessage tool(String content){
        return new MemoryMessage("tool", content);
    }

    public static MemoryMessage userImageUrl(String url){
        return mediaUrl("user", "image_url",url);
    }

    public static MemoryMessage userTxtUrl(String url){
        return mediaUrl("user", "txt_url",url);
    }

    public static MemoryMessage userMdxUrl(String url){
        return mediaUrl("user", "image_url",url);
    }

    public static MemoryMessage userPdfUrl(String url){
        return mediaUrl("user", "pdf_url",url);
    }
}

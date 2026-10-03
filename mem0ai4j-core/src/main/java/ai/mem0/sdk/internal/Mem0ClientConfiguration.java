package ai.mem0.sdk.internal;

import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;

public record Mem0ClientConfiguration(
        String baseUrl,
        String apikey,
        Mem0ApiProfile apiProfile,
        ObjectMapper  objectMapper,
        RestClient restClient
) {

    public Mem0ClientConfiguration {
        baseUrl = Objects.requireNonNull(baseUrl, "baseUrl is required");
        apikey = Objects.requireNonNull(apikey, "apikey is required");
        apiProfile = Objects.requireNonNull(apiProfile, "apiProfile is required");
        objectMapper = Objects.requireNonNull(objectMapper, "objectMapper is required");
        restClient = Objects.requireNonNull(restClient, "restClient is required");
    }
}

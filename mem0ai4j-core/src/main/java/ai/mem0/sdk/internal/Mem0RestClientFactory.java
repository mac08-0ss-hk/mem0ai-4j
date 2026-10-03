package ai.mem0.sdk.internal;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.Objects;

public final class Mem0RestClientFactory {

    private Mem0RestClientFactory() {}

    public static RestClient create(String baseUrl,Mem0ApiProfile apiProfile, String apiKey,
                                    RestClient.Builder restClientBuilder) {

        Objects.requireNonNull(baseUrl, "baseUrl must not be null");
        Objects.requireNonNull(apiProfile, "apiProfile must not be null");
        Objects.requireNonNull(apiKey, "apiKey must not be null");

        RestClient.Builder builder = restClientBuilder == null ? RestClient.builder() : restClientBuilder;
        RestClient.Builder configured = builder.baseUrl(baseUrl);
        if(apiProfile == Mem0ApiProfile.SELF_HOSTED_SERVER) {
            return configured
                    .defaultHeader("X-API-Key", apiKey)
                    .requestFactory(new SimpleClientHttpRequestFactory()) //TODO need to change to Apache HTTP Client later
                    .build();
        }
            return configured.defaultHeader("Authorization", "Token " + apiKey).build();
    }
}

package ai.mem0.sdk.client.builders;

import ai.mem0.sdk.client.Mem0Client;
import ai.mem0.sdk.client.Mem0ProjectClient;
import ai.mem0.sdk.client.impl.DefaultMem0Client;
import ai.mem0.sdk.exception.Mem0ConfigurationException;
import ai.mem0.sdk.internal.*;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

public final class Mem0ClientBuilder {

    private static final String DEFAULT_BASE_URL = "https://api.mem0.ai";

    private String apiKey;
    private String baseUrl = DEFAULT_BASE_URL;
    private Mem0ApiProfile apiProfile = Mem0ApiProfile.CLOUD;
    private ObjectMapper objectMapper;
    private RestClient  restClient;

    public Mem0ClientBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public Mem0ClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public Mem0ClientBuilder apiProfile(Mem0ApiProfile apiProfile) {
        this.apiProfile = apiProfile;
        return this;
    }

    public Mem0ClientBuilder objectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return this;
    }

    public Mem0ClientBuilder restClient(RestClient restClient) {
        this.restClient = restClient;
        return this;
    }

    public Mem0Client build() {
        if(apiKey == null || apiKey.isBlank()) {
            throw new Mem0ConfigurationException("apiKey is required");
        }
        ObjectMapper mapper = objectMapper == null
                ? Mem0ObjectMapperFactory.createDefault()
                : Mem0ObjectMapperFactory.configure(objectMapper.rebuild().build());

        RestClient client = restClient == null ?
               Mem0RestClientFactory.create(baseUrl, apiProfile, apiKey, null) : restClient;

        Mem0ClientConfiguration configuration = new Mem0ClientConfiguration(apiKey, baseUrl, apiProfile, mapper, client);
        return new DefaultMem0Client(configuration,
                new Mem0RequestExecutor(configuration.restClient(), configuration.objectMapper()));
    }
}


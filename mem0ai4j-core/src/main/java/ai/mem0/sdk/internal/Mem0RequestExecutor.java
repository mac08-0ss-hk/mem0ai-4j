package ai.mem0.sdk.internal;

import ai.mem0.sdk.exception.Mem0ApiException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public final class Mem0RequestExecutor {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public Mem0RequestExecutor(RestClient restClient, ObjectMapper objectMapper) {
        this.restClient = Objects.requireNonNull(restClient, "restClient is required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper is required");
    }

    public <T> T get(String path, Class<T> responseType)  {
        return exchange(path, HttpMethod.GET, null, responseType, Map.of());
    }

    public <T> T delete(String path, Class<T> responseType) {
        return exchange(path, HttpMethod.DELETE, null, responseType, Map.of());
    }

    public <T> T delete(String path, Object request, Class<T> responseType) {
        return exchange(path, HttpMethod.DELETE, request, responseType, Map.of());
    }

    public <T> T post(String path, Object request, Class<T> responseType) {
        return exchange(path, HttpMethod.POST, request, responseType, Map.of());
    }

    public <T> T post(String path, Object request, Class<T> responseType, Map<String, String> headers) {
        return exchange(path, HttpMethod.POST, request, responseType, headers);
    }

    public <T> T put(String path, Object request, Class<T> responseType) {
        return exchange(path, HttpMethod.PUT, request, responseType, Map.of());
    }

    public <T> T patch(String path, Object request, Class<T> responseType) {
        return exchange(path, HttpMethod.PATCH, request, responseType, Map.of());
    }

    public <T> T patch(String path, Object request, Class<T> responseType, Map<String, String> headers) {
        return exchange(path, HttpMethod.PATCH, request, responseType, headers);
    }

    private <T> T exchange(String path, HttpMethod method, Object request,
                           Class<T> responseType, Map<String, String> headers) {

        try{
            String response = prepareRequest(path, method, request, headers).retrieve()
                    .body(String.class);
            return parseResponse(response, responseType);
        }catch (RestClientResponseException exception){
            throw new Mem0ApiException("Mem0 API request Failed", exception.getStatusCode().value(),
                    exception.getResponseBodyAsString(), exception);
        }catch (JacksonException exception){
            throw new Mem0ApiException("Failed to serialize or deserialize Mem0 payload",
                    0,null, exception);
        }
    }

    private <T> T parseResponse(String response, Class<T> responseType) throws JacksonException {
        if(Objects.isNull(response) || response.isEmpty()){
            return null;
        }
        return objectMapper.readValue(response, responseType);
    }

    private RestClient.RequestBodySpec prepareRequest(String path,
                                                      HttpMethod method, Object request, Map<String, String> headers)
     throws JacksonException{

        RestClient.RequestBodyUriSpec methodSpec = restClient.method(method);
        RestClient.RequestBodySpec requestSpec = methodSpec.uri(path)
                                                    .accept(MediaType.APPLICATION_JSON);
        headers.forEach(requestSpec::header);

        if(request != null) {
           requestSpec.contentType(MediaType.APPLICATION_JSON)
                   .body(objectMapper.writeValueAsString(request));
        }
        return requestSpec;
    }

}

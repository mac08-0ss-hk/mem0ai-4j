package ai.mem0.sdk.exception;

public class Mem0ApiException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public Mem0ApiException(String message, int statusCode, String responseBody, Throwable cause ) {
        super(message, cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public  int getStatusCode() {
        return statusCode;
    }
    public String getResponseBody() {
        return responseBody;
    }
}

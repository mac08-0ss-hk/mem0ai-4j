package ai.mem0.sdk.exception;

public class Mem0ConfigurationException extends RuntimeException {

    public Mem0ConfigurationException(String message) {
        super(message);
    }

    public Mem0ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

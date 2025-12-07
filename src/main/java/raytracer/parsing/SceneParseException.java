package raytracer.parsing;

/**
 * Unchecked exception thrown when a scene description cannot be parsed.
 * Carries a human-readable message and an optional root cause.
 */
public final class SceneParseException extends RuntimeException {
    /**
     * Constructs a parsing exception with a message.
     *
     * @param message error description
     */
    public SceneParseException(String message) { super(message); }

    /**
     * Constructs a parsing exception with a message and a cause.
     *
     * @param message error description
     * @param cause   underlying exception
     */
    public SceneParseException(String message, Throwable cause) { super(message, cause); }
}

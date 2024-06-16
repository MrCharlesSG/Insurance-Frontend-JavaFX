package hr.algebra.javafxinsurance.serialization.exceptions;

public class NonSerializableClassException extends Exception{

    public NonSerializableClassException() {
    }

    public NonSerializableClassException(String message) {
        super(message);
    }

    public NonSerializableClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonSerializableClassException(Throwable cause) {
        super(cause);
    }

    public NonSerializableClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

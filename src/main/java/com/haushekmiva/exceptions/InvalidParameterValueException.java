package com.haushekmiva.exceptions;

public class InvalidParameterValueException extends ApplicationException {
    public InvalidParameterValueException(Throwable cause) {
        super(cause);
    }

    public InvalidParameterValueException(String message) {
        super(message);
    }

    public InvalidParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }
}

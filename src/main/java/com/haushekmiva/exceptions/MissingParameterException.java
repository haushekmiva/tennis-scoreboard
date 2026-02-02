package com.haushekmiva.exceptions;

public class MissingParameterException extends ApplicationException {
    public MissingParameterException(Throwable cause) {
        super(cause);
    }

    public MissingParameterException(String message) {
        super(message);
    }

    public MissingParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.haushekmiva.validation;

import java.util.*;

public final class ValidationErrorMessages {

    private final List<String> errorMessages = new ArrayList<>();

    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public List<String>  getErrorMessages() {
        return errorMessages;
    }

    public Optional<String> getFirstErrorMessage() {
        if (!errorMessages.isEmpty()) {
            return Optional.of(errorMessages.get(0));
        }

        return Optional.empty();
    }

    public boolean hasErrors() {
        return !errorMessages.isEmpty();
    }
}

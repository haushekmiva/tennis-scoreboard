package com.haushekmiva.validation;


public final class InputValidation {

    private InputValidation() {}

    public static void checkFieldEmpty(ValidationErrorMessages validationErrorMessages, String inputData, String errorMessage) {
        if (Checks.isEmpty(inputData)) {
            validationErrorMessages.addErrorMessage(errorMessage);
        }
    }



}

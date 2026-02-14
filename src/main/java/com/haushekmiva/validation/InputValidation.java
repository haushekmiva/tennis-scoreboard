package com.haushekmiva.validation;


public final class InputValidation {

    private InputValidation() {}

    public static void checkFieldEmpty(ValidationErrorMessages validationErrorMessages, String inputData, String errorMessage) {
        if (Checks.isEmpty(inputData)) {
            validationErrorMessages.addErrorMessage(errorMessage);
        }
    }

    public static void checkInputLength(ValidationErrorMessages validationErrorMessages, String inputData, int maxSize, String errorMessage) {
        if (inputData.length() > maxSize) {
            validationErrorMessages.addErrorMessage(errorMessage);
        }
    }

}

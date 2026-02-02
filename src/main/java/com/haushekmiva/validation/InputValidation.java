package com.haushekmiva.validation;


public class InputValidation {

    public static void checkFieldEmpty(ValidationErrorMessages validationErrorMessages, String inputData, String errorMessage) {
        if (Checks.isEmpty(inputData)) {
            validationErrorMessages.addErrorMessage(errorMessage);
        }
    }



}

package com.haushekmiva.validation;


public class FieldValidation {

    public static void checkFieldEmpty(ValidationErrorMessages validationErrorMessages, String fieldData, String errorMessage) {
        if ((fieldData == null) || (fieldData.isBlank())) {
            validationErrorMessages.addErrorMessage(errorMessage);
        }
    }

}

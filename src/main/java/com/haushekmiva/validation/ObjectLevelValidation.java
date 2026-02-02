package com.haushekmiva.validation;

public class ObjectLevelValidation {

    public static void checkFieldsEqual(ValidationErrorMessages errorMessages,
                                        String firstFieldData,
                                        String secondFieldData,
                                        String errorMessage) {

        if (firstFieldData.equals(secondFieldData)) {
            errorMessages.addErrorMessage(errorMessage);
        }
    }

}

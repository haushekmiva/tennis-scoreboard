package com.haushekmiva.validation;

public final class ObjectLevelValidation {

    private ObjectLevelValidation() {}

    public static void checkFieldsEqual(ValidationErrorMessages errorMessages,
                                        String firstFieldData,
                                        String secondFieldData,
                                        String errorMessage) {

        if (firstFieldData.equals(secondFieldData)) {
            errorMessages.addErrorMessage(errorMessage);
        }
    }

}

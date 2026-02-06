package com.haushekmiva.validation;

import com.haushekmiva.exceptions.MissingParameterException;


public final class RequestValidation {

    private RequestValidation() {}

    public static void checkRequestParameterEmpty(String requestParameter, String parameterName) {
        if (Checks.isEmpty(requestParameter)) {
                throw new MissingParameterException(String.format("Required parameter '%s' is missing.", parameterName));
        }
    }

}

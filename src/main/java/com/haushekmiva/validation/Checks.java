package com.haushekmiva.validation;

public final class Checks {

    private Checks() {}

    public static boolean isEmpty(String value) {
        return (value == null) || (value.isBlank());
    }

}

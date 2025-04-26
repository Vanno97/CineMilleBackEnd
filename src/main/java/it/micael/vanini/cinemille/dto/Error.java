package it.micael.vanini.cinemille.dto;

import java.util.HashMap;
import java.util.Map;

public record Error(int errorCode, String errorMessage) {
    private static final String FIRST_ERROR = "First error";

    private static final Map<String, Error> errors = new HashMap<>();

    static {
        errors.put(FIRST_ERROR, new Error(1, FIRST_ERROR));
    }

    public static Error getErrorByMessage(String errorMessage) {
        return errors.get(errorMessage);
    }
}

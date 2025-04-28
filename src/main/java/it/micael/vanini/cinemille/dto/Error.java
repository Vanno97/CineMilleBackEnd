package it.micael.vanini.cinemille.dto;

import java.util.HashMap;
import java.util.Map;

public record Error(int errorCode, String errorMessage) {
    public static final String FILM_NAME_NOT_FOUND = "Nome del film non trovato";
    public static final String INVALID_DATE_INTERVAL = "Intervallo di date non valido";

    public static final String USERNAME_NOT_FOUND = "L'username fornito non esiste";
    public static final String PASSWORD_NOT_VALID = "La password fornita è sbagliata";
    public static final String USERNAME_ALREADY_EXISTS = "L'username fornito già esiste";
    public static final String JWT_NOT_VALID = "Token JWT non valido";

    private static final Map<String, Error> errors = new HashMap<>();

    static {
        errors.put(FILM_NAME_NOT_FOUND, new Error(1, FILM_NAME_NOT_FOUND));
        errors.put(INVALID_DATE_INTERVAL, new Error(2, INVALID_DATE_INTERVAL));

        errors.put(USERNAME_NOT_FOUND, new Error(3, USERNAME_NOT_FOUND));
        errors.put(PASSWORD_NOT_VALID, new Error(4, PASSWORD_NOT_VALID));
        errors.put(USERNAME_ALREADY_EXISTS, new Error(5, USERNAME_ALREADY_EXISTS));
        errors.put(JWT_NOT_VALID, new Error(6, JWT_NOT_VALID));
    }

    public static Error getErrorByMessage(String errorMessage) {
        return errors.get(errorMessage);
    }
}

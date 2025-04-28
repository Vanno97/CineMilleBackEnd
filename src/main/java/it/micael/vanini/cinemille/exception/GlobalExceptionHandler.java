package it.micael.vanini.cinemille.exception;

import it.micael.vanini.cinemille.dto.Error;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CineMilleException.class)
    public ResponseEntity<Error> cineMilleException(@NotNull CineMilleException e) {
        return ResponseEntity.badRequest().body(Error.getErrorByMessage(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Error> usernameNotFoundException(@NotNull UsernameNotFoundException e) {
        return ResponseEntity.badRequest().body(Error.getErrorByMessage(e.getMessage()));
    }
}

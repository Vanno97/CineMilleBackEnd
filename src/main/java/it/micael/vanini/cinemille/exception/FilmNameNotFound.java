package it.micael.vanini.cinemille.exception;

import static it.micael.vanini.cinemille.dto.Error.FILM_NAME_NOT_FOUND;

public class FilmNameNotFound extends CineMilleException {
    public FilmNameNotFound() {
        super(FILM_NAME_NOT_FOUND);
    }
}

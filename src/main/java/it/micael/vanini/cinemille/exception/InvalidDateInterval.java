package it.micael.vanini.cinemille.exception;

import static it.micael.vanini.cinemille.dto.Error.INVALID_DATE_INTERVAL;

public class InvalidDateInterval extends CineMilleException {
    public InvalidDateInterval() {
        super(INVALID_DATE_INTERVAL);
    }
}

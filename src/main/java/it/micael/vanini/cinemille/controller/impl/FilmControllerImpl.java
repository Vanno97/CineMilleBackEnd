package it.micael.vanini.cinemille.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.micael.vanini.cinemille.controller.FilmController;
import it.micael.vanini.cinemille.dto.DateInterval;
import it.micael.vanini.cinemille.exception.CineMilleException;
import it.micael.vanini.cinemille.exception.InvalidDateInterval;
import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/film")
@Tag(name = "Film", description = "Servizio REST per recuperare le programmazioni dei film")
public class FilmControllerImpl implements FilmController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmControllerImpl.class);

    private final FilmService filmService;

    public FilmControllerImpl(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public List<Film> getAllStorico() {
        LOGGER.info("REST API: getAllStorico");
        return this.filmService.getAll();
    }

    @Override
    public List<Film> getAllStoricoFromToday() throws CineMilleException {
        LOGGER.info("REST API: getAllStoricoFromToday");
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = calendar.getTime();
        return this.filmService.getAllByProgrammazione(startDate, endDate);
    }

    @Override
    public List<Film> getAllStoricoFromTodayImax(boolean imax) throws CineMilleException {
        LOGGER.info("REST API: getAllStoricoFromToday");
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = calendar.getTime();
        return this.filmService.getAllByProgrammazioneAndImax(startDate, endDate, imax);
    }

    @Override
    public Film getStoricoByFilmName(String filmName) throws CineMilleException {
        LOGGER.info("REST API: getStoricoByFilmName");
        return this.filmService.getByFilmName(filmName);
    }

    @Override
    public Film getStoricoByFilmNameAndImax(String filmName, boolean imax) throws CineMilleException {
        LOGGER.info("REST API: getStoricoByFilmNameAndImax");
        return this.filmService.getByFilmNameAndImax(filmName, imax);
    }

    @Override
    public List<Film> getAllProgrammazioniInInterval(DateInterval interval) throws CineMilleException {
        LOGGER.info("REST API: getAllProgrammazioniInInterval");
        return this.filmService.getAllByProgrammazione(interval.getStartDate(), interval.getEndDate());
    }

    @Override
    public List<Film> getAllProgrammazioniInIntervalImax(DateInterval interval, boolean imax) throws InvalidDateInterval {
        LOGGER.info("REST API: getAllProgrammazioniInIntervalImax");
        return this.filmService.getAllByProgrammazioneAndImax(interval.getStartDate(), interval.getEndDate(), imax);
    }

    @Override
    public Film getAllProgrammazioneInIntervalByFilmName(DateInterval dateInterval, String filmName) throws CineMilleException {
        LOGGER.info("REST API: getAllProgrammazioneInIntervalByFilmName");
        return this.filmService.getAllByProgrammazioneAndFilmName(dateInterval.getStartDate(), dateInterval.getEndDate(), filmName);
    }

    @Override
    public Film getAllProgrammazioneInIntervalByFilmNameAndImax(DateInterval dateInterval, String filmName, boolean imax) throws CineMilleException {
        LOGGER.info("REST API: getAllProgrammazioneInIntervalByFilmNameAndImax");
        return this.filmService.getAllByProgrammazioneAndFilmNameAndImax(dateInterval.getStartDate(), dateInterval.getEndDate(), filmName, imax);
    }
}

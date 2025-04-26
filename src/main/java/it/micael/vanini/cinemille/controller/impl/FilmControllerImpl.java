package it.micael.vanini.cinemille.controller.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.micael.vanini.cinemille.controller.FilmController;
import it.micael.vanini.cinemille.dto.DateInterval;
import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.service.FilmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/film")
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
    public Film getStoricoByFilmId(String filmId) {
        return null;
    }

    @Override
    public Film getStoricoByFilmIdAndImax(String filmId, boolean imax) {
        return null;
    }

    @Override
    public List<Film> getStoricoByFilmName(String filmName) {
        return List.of();
    }

    @Override
    public List<Film> getStoricoByFilmNameAndImax(String filmName, boolean imax) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioniInInterval(DateInterval interval) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioniInIntervalImax(DateInterval interval, boolean imax) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioneInIntervalByFilmId(DateInterval interval, String filmId) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioneInIntervalByFilmIdAndImax(DateInterval dateInterval, String filmId, boolean imax) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioneInIntervalByFilmName(DateInterval dateInterval, String filmName) {
        return List.of();
    }

    @Override
    public List<Film> getAllProgrammazioneInIntervalByFilmNameAndImax(DateInterval dateInterval, String filmName, boolean imax) {
        return List.of();
    }
}

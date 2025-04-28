package it.micael.vanini.cinemille.service;

import it.micael.vanini.cinemille.exception.FilmNameNotFound;
import it.micael.vanini.cinemille.exception.InvalidDateInterval;
import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.model.Programmazione;
import it.micael.vanini.cinemille.model.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilmService implements BaseService<Film, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilmService.class);

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> getAll() {
        LOGGER.info("Retrieving all film from the database");
        List<Film> film = this.filmRepository.findAll();
        film.sort(Comparator.comparing(Film::getDataUscita));
        for (Film f : film) {
            f.getProgrammazioni().sort(Programmazione::compareTo);
        }
        LOGGER.info("Retrieved {} films from the database", film.size());
        return film;
    }

    @Override
    public Film getById(String id) {
        LOGGER.info("Trying to retrieve film with id: {} from the database", id);
        LOGGER.info("Checking if film with id {} exists", id);
        Optional<Film> film = this.filmRepository.findById(id);
        if (film.isPresent()) {
            LOGGER.info("Retrieved film with id {} from the database", id);
             return film.get();
        } else {
            LOGGER.error("Film with id {} not found", id);
            //TODO: Lanciare l'eccezzione specifica
            return null;
        }
    }

    @Override
    public Film insert(Film film) {
        LOGGER.info("Trying to insert film: {}", film);
        LOGGER.info("Checking if film with id {} exists", film.getIdFilm());
        if (film.getIdFilm() == null || !this.filmRepository.existsById(film.getIdFilm())) {
            LOGGER.info("Film with id {} not found, proceeding with insert", film.getIdFilm());
            return this.filmRepository.save(film);
        } else {
            LOGGER.info("Cannot insert film, film with same id already exists");
            //TODO: Gestire l'eccezzione e relativo errore
            return null;
        }
    }

    @Override
    public Film update(Film film) {
        LOGGER.info("Trying to update film: {}", film);
        LOGGER.info("Checking if film with id {} exists", film.getIdFilm());
        if (this.filmRepository.existsById(film.getIdFilm())) {
            LOGGER.info("Film with id {} exists, proceeding with update", film.getIdFilm());
            Film newFilm = this.filmRepository.save(film);
            LOGGER.info("Updated film with id {}", film.getIdFilm());
            return newFilm;
        } else {
            LOGGER.info("Cannot update, Film with id {} not found", film.getIdFilm());
            //TODO: Gestire l'errore e relativa eccezzione
            return null;
        }
    }

    @Override
    public void delete(String id) {
        LOGGER.info("Trying to delete Film with id: {}", id);
        LOGGER.info("Checking if Film with id {} exists", id);
        if (this.filmRepository.existsById(id)) {
            LOGGER.info("Film founded, proceeding with delete");
            this.filmRepository.deleteById(id);
        } else {
            LOGGER.error("Cannot delete, Film with id {} not found", id);
            //TODO: Lanciare la relativa eccezzione e gestire l'errore
        }
    }

    public boolean existsByFilmName(String filmName) {
        LOGGER.info("Checking if name with name {} exists", filmName);
        return this.filmRepository.existsByNomeFilm(filmName);
    }

    public Film getByFilmName(String filmName) throws FilmNameNotFound {
        LOGGER.info("Trying to retrieve film with name {} from database", filmName);
        LOGGER.info("Checking if film with name {} exists", filmName);
        Optional<Film> film = this.filmRepository.findByNomeFilm(filmName);
        if (film.isPresent()) {
            LOGGER.info("Retrieved sala with name {} from database", filmName);
            film.get().getProgrammazioni().sort(Programmazione::compareTo);
            return film.get();
        } else {
            LOGGER.error("Film with name {} not found", filmName);
            throw new FilmNameNotFound();
        }
    }

    public Film getByFilmNameAndImax(String filmName, boolean imax) throws FilmNameNotFound {
        LOGGER.info("Trying to retrieve film with name: {} and is imax: {} from database", filmName, imax);
        Film film = this.getByFilmName(filmName);
        LOGGER.info("Filtering only film that is projected on imax Sale? {}", imax);
        film.getProgrammazioni().removeIf(programmazione -> Boolean.compare(programmazione.getSala().isImax(), imax) != 0);
        if (film.getProgrammazioni().isEmpty()) {
            throw new FilmNameNotFound();
        }
        return film;
    }

    public List<Film> getAllByProgrammazione(Date startDate, Date endDate) throws InvalidDateInterval {
        if ((startDate == null || endDate == null) || (startDate.after(endDate) || startDate.equals(endDate))) {
            throw new InvalidDateInterval();
        }
        LOGGER.info("Trying to retrieve all Programmazione for all film in the interval {} -- {} from the database", startDate, endDate);
        List<Film> film = this.filmRepository.findAllByProgrammazioniInRange(startDate, endDate);
        for (Film f : film) {
            f.getProgrammazioni().removeIf(p -> p.getDataOra().before(startDate) || p.getDataOra().after(endDate));
            f.getProgrammazioni().sort(Programmazione::compareTo);
        }
        LOGGER.info("Retrieved {} Programmazione from the database", film.size());
        return film;
    }

    public List<Film> getAllByProgrammazioneAndImax(Date startDate, Date endDate, boolean imax) throws InvalidDateInterval {
        LOGGER.info("Trying to retrieve all Programmazione for all film in the interval {} -- {} from the database", startDate, endDate);
        List<Film> film = this.getAllByProgrammazione(startDate, endDate);
        if (imax) {
            Iterator<Film> it = film.iterator();
            while (it.hasNext()) {
                Film f = it.next();
                f.getProgrammazioni().removeIf(programmazione -> !programmazione.getSala().isImax());
                if (f.getProgrammazioni().isEmpty()) {
                    it.remove();
                } else {
                    f.getProgrammazioni().sort(Programmazione::compareTo);
                }
            }
        }
        LOGGER.info("Retrieved {} Programmazione from the database", film.size());
        return film;
    }

    public Film getAllByProgrammazioneAndFilmName(Date startDate, Date endDate, String filmName) throws FilmNameNotFound, InvalidDateInterval {
        if ((startDate == null || endDate == null) || (startDate.after(endDate) || startDate.equals(endDate))) {
            throw new InvalidDateInterval();
        }
        LOGGER.info("Trying to retrieve all Programmazione for film {} in the interval {} -- {} from the database", filmName, startDate, endDate);
        Optional<Film> film = this.filmRepository.findAllByNomeFilmProgrammazioniInRange(filmName, startDate, endDate);
        if (film.isPresent()) {
            film.get().getProgrammazioni().removeIf(p -> p.getDataOra().before(startDate) || p.getDataOra().after(endDate));
            film.get().getProgrammazioni().sort(Programmazione::compareTo);
            return film.get();
        } else {
            LOGGER.error("Film with name {} not found", filmName);
            throw new FilmNameNotFound();
        }
    }

    public Film getAllByProgrammazioneAndFilmNameAndImax(Date startDate, Date endDate, String filmName, boolean imax) throws FilmNameNotFound, InvalidDateInterval {
        LOGGER.info("Trying to retrieve all Programmazione for film {} in the interval {} -- {} and is imax: {} from the database", filmName, startDate, endDate, imax);
        Film film = this.getAllByProgrammazioneAndFilmName(startDate, endDate, filmName);
        film.getProgrammazioni().removeIf(programmazione -> Boolean.compare(programmazione.getSala().isImax(), imax) != 0);
        if (!film.getProgrammazioni().isEmpty()) {
            return film;
        } else {
            throw new FilmNameNotFound();
        }
    }
}

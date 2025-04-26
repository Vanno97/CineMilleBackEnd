package it.micael.vanini.cinemille.model.repository;

import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.model.Programmazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, String> {
    boolean existsByNomeFilm(String nomeFilm);

    List<Film> findAllByProgrammazioni(List<Programmazione> programmazione);

    List<Film> findAllByIdFilm(String idFilm);

    List<Film> findAllByIdFilmAndProgrammazioni(String idFilm, List<Programmazione> programmazione);

    Optional<Film> findByNomeFilm(String nomeFilm);

    List<Film> findAllByNomeFilmAndProgrammazioni(String nomeFilm, List<Programmazione> programmazione);
}

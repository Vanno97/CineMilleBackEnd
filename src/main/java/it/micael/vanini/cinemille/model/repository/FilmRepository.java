package it.micael.vanini.cinemille.model.repository;

import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.model.Programmazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, String> {
    boolean existsByNomeFilm(String nomeFilm);

    @Query("SELECT f FROM Film f JOIN f.programmazioni p WHERE p.dataOra BETWEEN :start AND :end")
    List<Film> findAllByProgrammazioniInRange(@Param("start") Date start, @Param("end") Date end);

    @Query("SELECT f FROM Film f JOIN f.programmazioni p WHERE f.nomeFilm = :nomeFilm AND p.dataOra BETWEEN :start AND :end")
    Optional<Film> findAllByNomeFilmProgrammazioniInRange(@Param("nomeFilm") String nomeFilm, @Param("start") Date start, @Param("end") Date end);

    Optional<Film> findByNomeFilm(String nomeFilm);
}

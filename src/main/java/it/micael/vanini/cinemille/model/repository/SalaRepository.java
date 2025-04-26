package it.micael.vanini.cinemille.model.repository;

import it.micael.vanini.cinemille.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, String> {
    boolean existsByNomeSala(String nomeSala);
    boolean existsByIdSalaOrNomeSala(String idSala, String nomeSala);
    List<Sala> findAllByImax(boolean imax);
    Optional<Sala> findByNomeSala(String nomeSala);
}

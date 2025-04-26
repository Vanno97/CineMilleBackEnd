package it.micael.vanini.cinemille.model.repository;

import it.micael.vanini.cinemille.model.Programmazione;
import it.micael.vanini.cinemille.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ProgrammazioniRepository extends JpaRepository<Programmazione, String> {
    List<Programmazione> findAllBySalaIn(List<Sala> sale);

    List<Programmazione> findAllByDataOraAfterAndDataOraBefore(Date startDate, Date endDate);

    List<Programmazione> findAllByDataOraAfterAndDataOraBeforeAndSalaIn(Date startDate, Date endDate, List<Sala> sale);
}

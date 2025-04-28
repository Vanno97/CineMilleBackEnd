package it.micael.vanini.cinemille.service;

import it.micael.vanini.cinemille.exception.InvalidDateInterval;
import it.micael.vanini.cinemille.model.Programmazione;
import it.micael.vanini.cinemille.model.Sala;
import it.micael.vanini.cinemille.model.repository.ProgrammazioniRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgrammazioneService implements BaseService<Programmazione, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgrammazioneService.class);

    private final ProgrammazioniRepository programmazioniRepository;

    public ProgrammazioneService(ProgrammazioniRepository programmazioniRepository) {
        this.programmazioniRepository = programmazioniRepository;
    }

    @Override
    public List<Programmazione> getAll() {
        LOGGER.info("Retrieving all programmazioni from the database");
        List<Programmazione> programmazione = programmazioniRepository.findAll();
        LOGGER.info("Retrieved {} programmazioni from the database", programmazione.size());
        return programmazione;
    }

    @Override
    public Programmazione getById(String idProgrammazione) {
        LOGGER.info("Tryin to retrieve programmazioni with id {} from database", idProgrammazione);
        LOGGER.info("Checking if programmazione with id {} exists", idProgrammazione);
        Optional<Programmazione> programmazione = this.programmazioniRepository.findById(idProgrammazione);
        if (programmazione.isPresent()) {
            LOGGER.info("Retrieved programmazione with id {} from database", idProgrammazione);
            return programmazione.get();
        } else {
            LOGGER.error("Programmazione with id {} not found", idProgrammazione);
            //TODO: Gestire errore ed eccezione
            return null;
        }
    }

    @Override
    public Programmazione insert(Programmazione programmazione) {
        LOGGER.info("Trying to insert programmazione: {}", programmazione);
        Programmazione insertedProgrammazione = programmazioniRepository.save(programmazione);
        LOGGER.info("Inserted programmazione: {}", insertedProgrammazione);
        return insertedProgrammazione;
    }

    @Override
    public Programmazione update(Programmazione programmazione) {
        LOGGER.info("Trying to update programmazione: {}", programmazione);
        LOGGER.info("Checking id programmazione with id: {} exists", programmazione.getIdProgrammazioni());
        if (this.programmazioniRepository.existsById(programmazione.getIdProgrammazioni())) {
            LOGGER.info("Programmazione with id: {} existsm, proceeding with update", programmazione.getIdProgrammazioni());
            Programmazione newProgrammazione = this.programmazioniRepository.save(programmazione);
            LOGGER.info("Updated programmazione with id: {}", newProgrammazione.getIdProgrammazioni());
            return newProgrammazione;
        } else {
            LOGGER.info("Cannot update, Programmazione with id {} not found", programmazione.getIdProgrammazioni());
            //TODO: Gestire eccezione ed errore
            return null;
        }
    }

    @Override
    public void delete(String id) {
        LOGGER.info("Trying to delete programmazione with id: {}", id);
        LOGGER.info("Checking if programmazione with id {} exists", id);
        if (this.programmazioniRepository.existsById(id)) {
            LOGGER.info("Programmazione founded, proceeding with delete");
            this.programmazioniRepository.deleteById(id);
        } else {
            LOGGER.error("Cannot delete, Programmazione with id {} not found", id);
            //TODO Lanciare la relativa eccezzione e gestire l'errore
        }
    }

    public List<Programmazione> getAllBySale(List<Sala> salaList) {
        LOGGER.info("Trying to retrieve alla Programmazioni for this Sale: {}", salaList.size());
        LOGGER.info("Checking if salaLista is not empty");
        if (!salaList.isEmpty()) {
            List<Programmazione> sale = this.programmazioniRepository.findAllBySalaIn(salaList);
            LOGGER.info("Retrieved {} Programmazioni from the database", salaList.size());
            return sale;
        } else {
            LOGGER.error("Cannot retrieve, Programmazione list is empty");
            //TODO: Lanciare e gestire l'eccezzione
            return null;
        }
    }

    public List<Programmazione> getAllByDateInterval(Date startDate, Date endDate) throws InvalidDateInterval {
        LOGGER.info("Trying to retrive Programmazione in the date interval {} -- {}", startDate, endDate);
        LOGGER.info("Checking if date is correct");
        if (endDate.after(startDate)) {
            LOGGER.info("Date is correct, proceeding with retrive from the database");
            List<Programmazione> programmazioni = this.programmazioniRepository.findAllByDataOraAfterAndDataOraBefore(startDate, endDate);
            LOGGER.info("Retrieved {} Programmazioni from the database", programmazioni.size());
            return programmazioni;
        } else {
            LOGGER.error("Cannot retrieve the list of Prgrammazione, the end date is before the start date");
            throw new InvalidDateInterval();
        }
    }

    public List<Programmazione> getAllBySaleAndDateInterval(List<Sala> sale, Date startDate, Date endDate) {
        LOGGER.info("Trying to retrieve Programmazione for Sale: {} in the date interval {} -- {}", sale, startDate, endDate);
        LOGGER.info("Checking if List of Sale is not empty");
        if (sale.isEmpty()) {
            LOGGER.error("Cannot retrive the list of Programmazioni, the list of sale is empty");
            //TODO: Gestire l'eccezzione e l'errore
            return null;
        }
        LOGGER.info("Checking if date is correct");
        if (endDate.before(startDate)) {
            LOGGER.info("Cannot retrieve the list of Programmazioni, the end date is before the start date");
            //TODO: Gestire l'eccezione e l'errore
            return null;
        }
        LOGGER.info("List of sale and date interval is correct, proceeding with retrive from the database");
        List<Programmazione> programmazioni = this.programmazioniRepository.findAllByDataOraAfterAndDataOraBeforeAndSalaIn(startDate, endDate, sale);
        LOGGER.info("Retrieved {} Programmazioni from the database", programmazioni.size());
        return programmazioni;
    }
}

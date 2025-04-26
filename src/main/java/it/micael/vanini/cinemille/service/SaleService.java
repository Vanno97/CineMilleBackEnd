package it.micael.vanini.cinemille.service;

import it.micael.vanini.cinemille.model.Sala;
import it.micael.vanini.cinemille.model.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService implements BaseService<Sala, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleService.class);

    private final SalaRepository salaRepository;

    public SaleService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public List<Sala> getAll() {
        LOGGER.info("Retrieving all sale from the database");
        List<Sala> salaList = salaRepository.findAll();
        LOGGER.info("Retrieved {} sale from the database", salaList.size());
        return salaList;
    }

    @Override
    public Sala getById(String idSala) {
        LOGGER.info("Trying to retrieve sala with id {} from the database", idSala);
        LOGGER.info("Checking if sala with id {} exists", idSala);
        Optional<Sala> sala = salaRepository.findById(idSala);
        if (sala.isPresent()) {
            LOGGER.info("Retrieved sala with id {} from the database", idSala);
            return sala.get();
        } else {
            LOGGER.error("Sala with id {} not found", idSala);
            //TODO: Lanciare l'eccezione specifica
            return null;
        }
    }

    @Override
    public Sala insert(Sala sala) {
        LOGGER.info("Trying to insert sala named: {}", sala.getNomeSala());
        LOGGER.info("Checking if sala with id {} or name {} exists", sala.getNomeSala(), sala.getNomeSala());
        if (!this.salaRepository.existsByIdSalaOrNomeSala(sala.getIdSala(), sala.getNomeSala())) {
            LOGGER.info("Sala not found, proceeding with insert");
            return this.salaRepository.save(sala);
        } else {
            LOGGER.info("Cannot insert, sala with same id or name already exists");
            //TODO: Gestire l'errore e relativa eccezzione
            return null;
        }
    }

    @Override
    public Sala update(Sala sala) {
        LOGGER.info("Trying to insert thew new sala named: {}", sala.getNomeSala());
        LOGGER.info("Checking if sala with id {} exists", sala.getIdSala());
        if (this.salaRepository.existsById(sala.getIdSala())) {
            LOGGER.info("Sala with id {} exists, proceeding with update", sala.getIdSala());
            Sala newSala = salaRepository.save(sala);
            LOGGER.info("Updated sala with id: {}", newSala.getIdSala());
            return newSala;
        } else {
            LOGGER.error("Cannot update, Sala with id {} not found", sala.getIdSala());
            //TODO: Gestire errore ed eccezzione
            return null;
        }
    }

    @Override
    public void delete(String idSala) {
        LOGGER.info("Trying to delete sala with id: {}", idSala);
        LOGGER.info("Checking if sala with id {} exists", idSala);
        if (this.salaRepository.existsById(idSala)) {
            LOGGER.info("Sala founded, proceeding with delete");
            this.salaRepository.deleteById(idSala);
        } else {
            LOGGER.error("Cannot delete, Sala with id {} not found", idSala);
            //TODO: Lanciare la relativa eccezzione e gestire l'errore
        }
    }

    public Sala getByNomeSala(String nomeSala) {
        LOGGER.info("Trying to retrieve sala with name {} from database", nomeSala);
        LOGGER.info("Checking if sala with name {} exists", nomeSala);
        Optional<Sala> sala = this.salaRepository.findByNomeSala(nomeSala);
        if (sala.isPresent()) {
            LOGGER.info("Retrieved sala with name {} from database", nomeSala);
            return sala.get();
        } else {
            LOGGER.error("Sala with name {} not found", nomeSala);
            //TODO: Gestire errore ed eccezione
            return null;
        }
    }

    public List<Sala> getAllByImax(boolean imax) {
        LOGGER.info("Retreving all sale with imax {}, from the database", imax);
        List<Sala> salaList = this.salaRepository.findAllByImax(imax);
        LOGGER.info("Retreved {} sale from the database", salaList.size());
        return salaList;
    }
}

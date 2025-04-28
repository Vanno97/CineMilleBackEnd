package it.micael.vanini.cinemille.util;

import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.model.Programmazione;
import it.micael.vanini.cinemille.model.Sala;
import it.micael.vanini.cinemille.service.FilmService;
import it.micael.vanini.cinemille.service.ProgrammazioneService;
import it.micael.vanini.cinemille.service.SaleService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExcelParser {
    private final Logger LOGGER = LoggerFactory.getLogger(ExcelParser.class);

    private final String excelOriginFolder;
    private final String excelDestFolder;

    private final FilmService filmService;
    private final ProgrammazioneService programmazioneService;
    private final SaleService saleService;

    public ExcelParser(
            @Value("${it.micael.vanini.excel.origin-folder}") String excelOriginFolder,
            @Value("${it.micael.vanini.excel.destination-folder}") String excelDestFolder,
            FilmService filmService,
            ProgrammazioneService programmazioneService,
            SaleService saleService
    ) {
        this.excelOriginFolder = excelOriginFolder;
        this.excelDestFolder = excelDestFolder;
        this.filmService = filmService;
        this.programmazioneService = programmazioneService;
        this.saleService = saleService;
    }

    @Scheduled(cron = "0 0 1 * * MON", zone = "Europe/Rome")
    @Transactional
    public void parseExcel() {
        File originFolder = new File(excelOriginFolder);
        File destFolder = new File(excelDestFolder);
        if (!originFolder.exists()) {
            LOGGER.error("Folders for managing excels not exists");
            return;
        }
        File[] excelFiles = originFolder.listFiles();
        if (excelFiles == null || excelFiles.length == 0) {
            LOGGER.info("Non file to process");
            return;
        }
        for (File file : excelFiles) {
            if (file.isFile()) {
                try (
                        FileInputStream fis = new FileInputStream(file);
                        Workbook workbook = new XSSFWorkbook(fis);
                ) {
                    Sheet sheet = workbook.getSheetAt(0);
                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        if (row.getCell(0) != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat fullFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            String filmName = row.getCell(0).getStringCellValue();
                            LOGGER.info("Reading programmazione for {}", file.getName());
                            Film film = null;
                            LOGGER.info("Checking if film exists");
                            if (!this.filmService.existsByFilmName(filmName)) {
                                LOGGER.info("Film {} not exists, proceeding to create", filmName);
                                Date date = row.getCell(2).getDateCellValue();
                                film = new Film(null, filmName, date, new ArrayList<>());
                                film = this.filmService.insert(film);
                            } else {
                                LOGGER.info("Film {} already exists, proceeding to retrieve it", filmName);
                                film = this.filmService.getByFilmName(filmName);
                            }
                            LOGGER.info("Retrieving Sala for programmazione for {}", file.getName());
                            String salaName = row.getCell(1).getStringCellValue();
                            Sala sala = this.saleService.getByNomeSala(salaName);
                            LOGGER.info("Creating single programmazione for film {}", filmName);
                            String dataProgrammazione = dateFormat.format(row.getCell(3).getDateCellValue());
                            String oraProgrammazione = timeFormat.format(row.getCell(4).getDateCellValue());
                            Date dataOra = fullFormat.parse(String.format("%s %s", dataProgrammazione, oraProgrammazione));
                            Programmazione programmazione = new Programmazione(null, dataOra, sala);
                            programmazione = this.programmazioneService.insert(programmazione);
                            film.getProgrammazioni().add(programmazione);
                            this.filmService.update(film);
                        }
                    }
                    file.renameTo(new File(destFolder.getAbsoluteFile() + "/" + file.getName()));
                } catch (IOException e) {
                    LOGGER.error("Error reading excel", e);
                } catch (Exception e) {
                    LOGGER.error("Error reading excel cell data", e);
                }
            }
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void bootParser() {
        LOGGER.info("Starting parser on boot, checking if there is no film");
        List<Film> films = this.filmService.getAll();
        if (films.isEmpty()) {
            LOGGER.info("No films found, proceeding to create");
            this.parseExcel();
        } else {
            LOGGER.info("Found films, not starting the boot parser");
        }
    }
}

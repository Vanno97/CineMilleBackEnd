package it.micael.vanini.cinemille.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.micael.vanini.cinemille.dto.DateInterval;
import it.micael.vanini.cinemille.dto.Error;
import it.micael.vanini.cinemille.exception.CineMilleException;
import it.micael.vanini.cinemille.model.Film;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FilmController {
    @GetMapping("/")
    @Operation(
            summary = "Storico delle programmazioni",
            description = "Recupera tutte le programmazione, anche quelle passate, di tutti i film",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Film.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    List<Film> getAllStorico() throws CineMilleException;

    @GetMapping("/now")
    @Operation(
            summary = "Programmazioni attive",
            description = "Recupera tutte le programmazione di una settimana a partire da oggi",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Film.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    List<Film> getAllStoricoFromToday()  throws CineMilleException;

    @GetMapping("/now/imax/{imax}")
    @Operation(
            summary = "Programmazioni attive con scelta IMAX",
            description = "Recupera tutte le programmazione di una settimana a partire da oggi potendo scegliere tra IMAX e non IMAX",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = Film.class
                                            )
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    List<Film> getAllStoricoFromTodayImax(
            @PathVariable("imax") @Parameter(description = "Boolean per determinare se sale IMAX o non IMAX", required = true) boolean imax
    )  throws CineMilleException;

    @GetMapping("/name/{filmName}")
    @Operation(
            summary = "Programmazioni di un film",
            description = "Recupera le programmazioni del film fornendo il nome, recupera la programmazione di una settimana a partire dalla data odierna",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "404",
                            description = "Nome del film non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    Film getStoricoByFilmName(
            @PathVariable("filmName") @Parameter(description = "Nome del film da cercare", required = true) String filmName
    ) throws CineMilleException;

    @GetMapping("/name/imax/{filmName}/{imax}")
    @Operation(
            summary = "Programmazioni di un film",
            description = "Recupera le programmazioni film fornendo il nome potendo scegliere tra IMAX e non IMAX, recupera la programmazione di una settimana a partire dalla data odierna",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "404",
                            description = "Nome del film non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    Film getStoricoByFilmNameAndImax(
            @PathVariable("filmName") @Parameter(description = "Nome del film da cercare") String filmName,
            @PathVariable("imax") @Parameter(description = "Boolean per determinare se sale IMAX o non IMAX", required = true) boolean imax
    ) throws CineMilleException;

    @PostMapping("/interval")
    @Operation(
            summary = "Tutte le programmazioni di tutti i film in un dato intervallo di date",
            description = "Recupera le programmazioni di tutti i film nell'intervallo di date fornito",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "400",
                            description = "Dati forniti non corretti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    List<Film> getAllProgrammazioniInInterval(
            @RequestBody @Parameter(description = "Intervallo di date in cui fare la ricerca", required = true) DateInterval interval
    ) throws CineMilleException;

    @PostMapping("/interval/imax/{imax}")
    @Operation(
            summary = "Tutte le programmazioni di tutti i film in un dato intervallo di date",
            description = "Recupera le programmazioni di tutti i film nell'intervallo di date fornito potendo scegliere tra IMAX e non IMAX",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "400",
                            description = "Dati forniti non corretti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    List<Film> getAllProgrammazioniInIntervalImax(
            @RequestBody @Parameter(description = "Intervallo di date in cui fare la ricerca", required = true) DateInterval interval,
            @PathVariable("imax") @Parameter(description = "Boolean per determinare se sale IMAX o non IMAX", required = true) boolean imax
    ) throws CineMilleException;


    @PostMapping("/interval/name/{filmName}")
    @Operation(
            summary = "Programmazioni del film richiesto in un dato intervallo di date",
            description = "Recupera tutte le programmazioni del film richiesto nell'intervallo di date fornito",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "404",
                            description = "Film non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    Film getAllProgrammazioneInIntervalByFilmName(
            @RequestBody @Parameter(description = "Intervallo di date in cui fare la ricerca", required = true) DateInterval dateInterval,
            @PathVariable("filmName") @Parameter(description = "Nome del film da cercare", required = true) String filmName
    ) throws CineMilleException;

    @PostMapping("/interval/name/imax/{filmName}/{imax}")
    @Operation(
            summary = "Programmazioni del film richiesto in un dato intervallo di date",
            description = "Recupera tutte le programmazioni del film richiesto nell'intervallo di date fornito potendo scegliere tra IMAX e non IMAX",
            responses = {
                    @ApiResponse (
                            responseCode = "200",
                            description = "Dati Restituiti",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Film.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "404",
                            description = "Film non trovato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "500",
                            description = "Errore interno"
                    )
            }
    )
    Film getAllProgrammazioneInIntervalByFilmNameAndImax(
            @RequestBody @Parameter(description = "Intervallo di date in cui effettuare la ricerca") DateInterval dateInterval,
            @PathVariable("filmName") @Parameter(description = "Nome del film da cercare", required = true) String filmName,
            @PathVariable("imax") @Parameter(description = "Boolean per determinare se sale IMAX o non IMAX", required = true) boolean imax
    ) throws CineMilleException;
}

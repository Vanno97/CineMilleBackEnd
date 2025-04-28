package it.micael.vanini.cinemille.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.micael.vanini.cinemille.model.Film;
import it.micael.vanini.cinemille.model.Sala;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface SaleController {
    @GetMapping("/")
    @Operation(
            summary = "Lista delle sale",
            description = "Recupera tutte le sale presenti nel cinema",
            responses = {
                    @ApiResponse(
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
    List<Sala> getAll();
}

package it.micael.vanini.cinemille.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.micael.vanini.cinemille.dto.LoginRequest;
import it.micael.vanini.cinemille.dto.LoginResponse;
import it.micael.vanini.cinemille.dto.SignUpRequest;
import it.micael.vanini.cinemille.exception.CineMilleException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthenticationController {
    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "API Rest per il login",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login effettuato",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = LoginResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Username not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = Error.class
                                    )
                            )
                    ),
                    @ApiResponse (
                            responseCode = "403",
                            description = "Credenziali errate",
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
    LoginResponse login(@RequestBody @Parameter(description = "Dati utente", required = true) LoginRequest loginRequest) throws CineMilleException;

    @PostMapping("/signup")
    @Operation(
            summary = "Login",
            description = "API Rest per il login",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login effettuato"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Username already used",
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
    void signup(@RequestBody @Parameter(description = "Dati utente", required = true) SignUpRequest signUpRequest) throws CineMilleException;

    @PostMapping("/validate/{token}")
    @Operation(
            summary = "Check JWT token",
            description = "API Rest per controllare il JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Jwt valido"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Jwt non valido",
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
    void validateToken(@PathVariable("token") @Parameter(description = "Dati utente", required = true) String token) throws CineMilleException;
}

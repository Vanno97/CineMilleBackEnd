package it.micael.vanini.cinemille.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
@AllArgsConstructor
@Schema(description = "Rappresentazione dell'intervallo di date per la ricerca")
public class DateInterval {
    @Schema(description = "Data di inizio ricerca")
    private Date startDate;
    @Schema(description = "Data di fine ricerca")
    private Date endDate;
}

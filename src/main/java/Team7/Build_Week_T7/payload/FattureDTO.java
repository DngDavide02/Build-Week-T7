package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FattureDTO(
        @NotNull(message = "La data è obbligatoria")
        LocalDate data,
        @NotNull(message = "L'importo è obbligatoria")
        int importo,
        @NotNull(message = "Il numero di fattura è obbligatoria")
        int numero,
        String statoFatture
) {}
package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FattureUpdateDTO(
        @NotNull(message = "La data non può essere null")
        LocalDate data,
        @Min(value = 0, message = "L'importo deve essere maggiore o uguale a 0")
        Integer importo,
        @Min(value = 0, message = "Il numero deve essere maggiore o uguale a 0")
        Integer numero
) { }

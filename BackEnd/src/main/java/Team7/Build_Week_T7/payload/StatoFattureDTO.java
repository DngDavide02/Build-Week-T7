package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.NotEmpty;

public record StatoFattureDTO(
        @NotEmpty(message = "Stato fattura è necessario")
        String stato,String descrizione) {
}

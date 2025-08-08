package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IndirizziDTO(@NotEmpty(message = "La via è obbligatorio!")
                           String via,
                           @NotNull(message = "Il civico è obbligatorio!")
                           int civico,
                           @NotEmpty(message = "La località è obbligatoria!")
                           String localita,
                           @NotNull(message = "Il cap è obbligatorio!")
                           int cap,
                           @NotNull(message = "L'id del comune è obbligatorio!")
                           Long comuneId
) {
}

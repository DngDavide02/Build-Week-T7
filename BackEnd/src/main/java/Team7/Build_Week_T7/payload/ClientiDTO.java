package Team7.Build_Week_T7.payload;

import Team7.Build_Week_T7.entities.TipoCliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClientiDTO(
        @NotEmpty(message = "regione sociale obbligatoria")
        String ragioneSociale,
        @NotEmpty(message = "partita iva obbligatoria")
        String partitaIVA,
        @NotEmpty(message = "email obbligatoria")
        String email,
        @NotNull(message = "data ultimo contatto obbligatoria")
        LocalDate dataUltimoContatto,
        @NotNull(message = "fatturato obbligatorio")
        int fatturatoAnnuale,
        @NotEmpty(message = "pec obbligatoria")
        String pec,
        @NotEmpty(message = "telefono obbligatorio")
        String telefono,
        @NotEmpty(message = "email di contatto obbligatoria")
        String emailContatto,
        @NotEmpty(message = "nome contatto obbligatorio")
        String nomeContatto,
        @NotEmpty(message = "cognome contatto obbligatorio")
        String cognomeContatto,
        @NotEmpty(message = "telefono contatto obbligatorio")
        String telefonoContatto,
        @NotNull(message = "tipo obbligatorio")
        TipoCliente tipoCliente,
        @NotEmpty(message = "La via è obbligatorio!")
        String via,
        @NotNull(message = "Il civico è obbligatorio!")
        int civico,
        @NotEmpty(message = "La località è obbligatoria!")
        String localita,
        @NotNull(message = "Il cap è obbligatorio!")
        int cap,
        @NotNull(message = "L'id del comune è obbligatorio!")
        Long comuneId

) {}

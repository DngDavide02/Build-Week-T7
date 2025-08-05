package Team7.Build_Week_T7.payload;

import Team7.Build_Week_T7.entities.TipoCliente;

import java.time.LocalDate;

public record ClientiUpdateDTO(
        String ragioneSociale,
        String partitaIVA,
        String email,
        LocalDate dataUltimoContatto,
        Integer fatturatoAnnuale,
        String pec,
        String telefono,
        String emailContatto,
        String nomeContatto,
        String cognomeContatto,
        String telefonoContatto,
        String logoAziendale,
        TipoCliente tipoCliente
) {}

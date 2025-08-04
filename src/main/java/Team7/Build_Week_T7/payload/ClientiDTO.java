package Team7.Build_Week_T7.payload;

import Team7.Build_Week_T7.entities.TipoCliente;

import java.time.LocalDate;

public record ClientiDTO(
        Long id,
        String ragioneSociale,
        String partitaIva,
        String email,
        LocalDate dataInserimento,
        LocalDate dataUltimoContatto,
        int fatturatoAnnuale,
        String pec,
        String telefono,
        String emailContatto,
        String nomeContatto,
        String cognomeContatto,
        String telefonoContatto,
        String logoAziendale,
        TipoCliente tipoCliente,
        IndirizziDTO sedeLegale,
        IndirizziDTO sedeOperativa
) {}

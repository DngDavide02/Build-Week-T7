package Team7.Build_Week_T7.payload;

import java.time.LocalDate;

public record FattureDTO(
        LocalDate data,
        int importo,
        int numero,
        Long statoFattureId,
        Long clientiId
) {}
package Team7.Build_Week_T7.payload.FattureDTO;

import java.time.LocalDateTime;

public record FattureDTO(
    Long id,
    LocalDateTime data,
    int importo,
    int numero,
    Long statoFattureId,
    Long clientiId
) {}
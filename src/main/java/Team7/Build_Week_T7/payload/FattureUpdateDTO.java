package Team7.Build_Week_T7.payload;

import java.time.LocalDate;

public record FattureUpdateDTO(
        LocalDate data,
        Integer importo,
        Integer numero
) { }

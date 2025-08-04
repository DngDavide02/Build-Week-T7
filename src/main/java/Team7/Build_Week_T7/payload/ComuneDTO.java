package Team7.Build_Week_T7.payload;

import com.opencsv.bean.CsvBindByPosition;

public record ComuneDTO(
        @CsvBindByPosition(position = 0)
        String codiceProvincia,

        @CsvBindByPosition(position = 1)
        String progressivoComune,

        @CsvBindByPosition(position = 2)
        String denominazione,

        @CsvBindByPosition(position = 3)
        String provincia
) { }

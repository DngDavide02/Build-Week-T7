package Team7.Build_Week_T7.payload;

import com.opencsv.bean.CsvBindByPosition;

public record ProvinciaDTO (

        @CsvBindByPosition(position = 0)
        String sigla,

        @CsvBindByPosition(position = 1)
        String provincia,

        @CsvBindByPosition(position = 2)
        String regione

) {}

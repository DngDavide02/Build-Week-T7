package Team7.Build_Week_T7.payload;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProvinciaDTO {
        @CsvBindByPosition(position = 0)
        private String sigla;

        @CsvBindByPosition(position = 1)
        private String provincia;

        @CsvBindByPosition(position = 2)
        private String regione;
}

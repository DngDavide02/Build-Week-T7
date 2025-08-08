package Team7.Build_Week_T7.payload;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ComuneDTO {
        @CsvBindByPosition(position = 0)
        private String codiceProvincia;

        @CsvBindByPosition(position = 1)
        private String progressivoComune;

        @CsvBindByPosition(position = 2)
        private String denominazione;

        @CsvBindByPosition(position = 3)
        private String provincia;
}

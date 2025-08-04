package Team7.Build_Week_T7.entities;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "comune")
@NoArgsConstructor
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codiceProvincia;
    private String progressivoComune;
    private String denominazione;
    @ManyToOne
    private Provincia provincia;
}

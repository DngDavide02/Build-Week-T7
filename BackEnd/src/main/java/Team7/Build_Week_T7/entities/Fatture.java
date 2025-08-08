package Team7.Build_Week_T7.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Entity
@Data
@NoArgsConstructor
public class Fatture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private LocalDate data;
    private int importo;
    private int numero;

    @ManyToOne
    @JoinColumn(name = "stato_fatture_id")
    private StatoFatture statoFatture;

    @ManyToOne
    private Clienti clienti;

    public Fatture(LocalDate data, int importo, int numero, StatoFatture statoFatture, Clienti clienti) {
        this.data = data;
        this.importo = importo;
        this.numero = numero;
        this.statoFatture = statoFatture;
        this.clienti = clienti;
    }
}

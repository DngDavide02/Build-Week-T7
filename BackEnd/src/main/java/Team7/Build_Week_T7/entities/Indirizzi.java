package Team7.Build_Week_T7.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "indirizzi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Indirizzi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String via;
    private int civico;
    private String localita;
    private int cap;
    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;

    public Indirizzi(String via, int civico, String localita, int cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }
}

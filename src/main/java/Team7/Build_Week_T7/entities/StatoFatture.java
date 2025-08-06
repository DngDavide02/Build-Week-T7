package Team7.Build_Week_T7.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StatoFatture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stato;
    private String descrizione;

    private boolean attivo=true;


    public StatoFatture(String stato,String descrizione) {
        this.stato = stato;
        this.descrizione = descrizione;
    }
}

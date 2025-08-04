package Team7.Build_Week_T7.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Data
@NoArgsConstructor
public class Fatture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private LocalDateTime data;
    private int importo;
    private int numero;

    @ManyToOne
    @JoinColumn(name = "stato_fatture_id")
    private StatoFatture statoFatture;
}

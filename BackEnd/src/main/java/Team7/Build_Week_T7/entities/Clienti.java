package Team7.Build_Week_T7.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "clienti")
@NoArgsConstructor
public class Clienti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String ragioneSociale;
    private String partitaIVA;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private int fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nome;
    private String cognome;
    private String telefonoContatto;
    private String logoAziendale;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_legale_id")
    private Indirizzi sedeLegale;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_operativa_id")
    private Indirizzi sedeOperativa;

    public Clienti(String ragioneSociale, String partitaIVA, String email, LocalDate dataInserimento,
                   LocalDate dataUltimoContatto, int fatturatoAnnuale, String pec, String telefono,
                   String emailContatto, String nome, String cognome, String telefonoContatto,
                   TipoCliente tipoCliente, Indirizzi sedeLegale, Indirizzi sedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIVA = partitaIVA;
        this.email = email;
        this.dataInserimento = LocalDate.now();
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nome = nome;
        this.cognome = cognome;
        this.telefonoContatto = telefonoContatto;
        this.tipoCliente = tipoCliente;
        this.sedeLegale = sedeLegale;
        this.sedeOperativa = sedeOperativa;
    }
}

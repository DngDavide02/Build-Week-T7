package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientiRepository extends JpaRepository<Clienti, Long> {
    boolean existsByPartitaIVA(String partitaIVA);

    List<Clienti> findByTipoCliente(TipoCliente tipoCliente);

    Clienti findByEmail(String email);

    List<Clienti> findAllByOrderByCognomeContattoAsc();

    List<Clienti> findAllByOrderByFatturatoAnnualeAsc();

    List<Clienti> findAllByOrderByDataInserimentoAsc();

    List<Clienti> findAllByOrderByDataUltimoContattoAsc();

    List<Clienti> findByFatturatoAnnualeBetween(int min, int max);

    List<Clienti> findByDataInserimentoGreaterThan(LocalDate min);

    List<Clienti> findByDataUltimoContattoGreaterThan(LocalDate min);

    List<Clienti> findByCognomeContattoIgnoreCaseLike(String cognome);

}

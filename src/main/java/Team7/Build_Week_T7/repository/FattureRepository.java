package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.entities.StatoFatture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FattureRepository extends JpaRepository<Fatture, Long> {
    // ricerca fatture per cliente
    List<Fatture> findByClienti(Clienti cliente);

    List<Fatture> findByStatoFatture(StatoFatture statoFatture);

    List<Fatture> findByDataGreaterThan(LocalDate min);

    List<Fatture> findByDataBetween(LocalDate min, LocalDate max);

    List<Fatture> findByImportoBetween(int min, int max);
}

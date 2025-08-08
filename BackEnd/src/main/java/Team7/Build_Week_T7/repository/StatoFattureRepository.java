package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.StatoFatture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatoFattureRepository extends JpaRepository<StatoFatture, Long> {
    Optional<StatoFatture> findByStato(String stato);
    List<StatoFatture> findByAttivoTrue();
    boolean existsByStato(String stato);
}


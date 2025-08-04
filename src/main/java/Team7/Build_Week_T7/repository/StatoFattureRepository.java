package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.StatoFatture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatoFattureRepository extends JpaRepository<StatoFatture, Long> {
}

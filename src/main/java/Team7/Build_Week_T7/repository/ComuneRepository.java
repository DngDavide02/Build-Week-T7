package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Optional<Comune> findByProvincia(Provincia provincia);
    Optional<Comune> findByDenominazioneContainingIgnoreCase(String denominazione);
    boolean existsByDenominazione(String denominazione);
}

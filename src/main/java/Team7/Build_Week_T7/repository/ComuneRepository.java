package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Long> {
    List<Comune> findByProvincia(Provincia provincia);
    List<Comune> findByDenominazioneContainingIgnoreCase(String denominazione);
    boolean existsByDenominazione(String denominazione);
}

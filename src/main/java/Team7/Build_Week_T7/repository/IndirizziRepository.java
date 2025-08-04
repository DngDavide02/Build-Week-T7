package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Indirizzi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndirizziRepository extends JpaRepository <Indirizzi, Long> {
    List<Indirizzi> findByComune(Comune comune);
    List<Indirizzi> findByCap(int cap);
    List<Indirizzi> findByLocalitaContainingIgnoreCase(String localita);
}

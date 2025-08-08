package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    boolean existsByProvinciaIgnoreCase(String provincia);
    Optional<Provincia> findByProvinciaIgnoreCase(String provincia);
}

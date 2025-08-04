package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, String> {
    Optional<Provincia> existsByProvincia(String provincia);
    Optional<Provincia> findByProvincia (String provincia);
}

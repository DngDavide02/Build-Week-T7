package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientiRepository extends JpaRepository <Clienti, Long> {
    boolean existByPartitaIVA (String partitaIVA);
    List<Clienti> findByTipoCliente(TipoCliente tipoCliente);
    Clienti findByEmail(String email);
}

package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Clienti;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ClientiRepositoryCustomImpl implements ClientiRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Clienti saveCustom(Clienti cliente) {
        entityManager.persist(cliente);
        return cliente;
    }
}

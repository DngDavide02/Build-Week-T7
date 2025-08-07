package Team7.Build_Week_T7.specification;

import Team7.Build_Week_T7.entities.Clienti;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class ClientiSpec {
    public static Specification<Clienti> ordinePerCognomi(Specification<Clienti> specification) {
        return ((root, query, builder) -> {
            query.orderBy(builder.asc(root.get("cognome")));
            return null;
        });
    }
}



//List<Clienti> findAllByOrderByCognomeContattoAsc();
//
//List<Clienti> findAllByOrderByFatturatoAnnualeAsc();
//
//List<Clienti> findAllByOrderByDataInserimentoAsc();
//
//List<Clienti> findAllByOrderByDataUltimoContattoAsc();
//
//List<Clienti> findByFatturatoAnnualeBetween(int min, int max);
//
//List<Clienti> findByDataInserimentoGreaterThan(LocalDate min);
//
//List<Clienti> findByDataUltimoContattoGreaterThan(LocalDate min);
//
//List<Clienti> findByCognomeContattoIgnoreCaseLike(String cognome);
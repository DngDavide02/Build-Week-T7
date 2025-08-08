package Team7.Build_Week_T7.specification;

import Team7.Build_Week_T7.entities.Clienti;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class ClientiSpec {
//    public static Specification<Clienti> ordinePerCognomi() {
//        return ((root, query, builder) -> {
//            query.orderBy(builder.asc(root.get("cognome")));
//            return builder.conjunction();
//        });
//    }//fine ordina per cognomi
//
//    public static Specification<Clienti> ordinaPerFatturato() {
//        return ((root, query, builder) -> {
//            query.orderBy(builder.asc(root.get("fatturatoAnnuale")));
//            return builder.conjunction();
//        });
//    }
//
//    public static Specification<Clienti> ordinaPerDataInserimento(){
//        return ((root, query, builder) -> {
//            query.orderBy(builder.asc(root.get("dataInserimento")));
//            return builder.conjunction();
//        });
//    }
//
//    public static Specification<Clienti> ordinaPerDataUltimoContratto(){
//        return ((root, query, builder) -> {
//            query.orderBy(builder.asc(root.get("dataUltimoContratto")));
//            return builder.conjunction();
//        });
//    }

    public static Specification<Clienti> fatturatoMaggioreDi(Integer max) {
        return (root, query, builder) ->
                builder.greaterThan(root.get("fatturatoAnnuale"), max);
    }

    public static Specification<Clienti> fatturatoMinoreeDi(Integer min) {
        return (root, query, builder) ->
                builder.lessThan(root.get("fatturatoAnnuale"), min);
    }

    public static Specification<Clienti> fatturatoCompresoTra(Integer min, Integer max) {
        return (root, query, builder) ->
                builder.between(root.get("fatturatoAnnuale"), min, max);
    }


    public static Specification<Clienti> dataDiInserimentoMaggioreDi(LocalDate date) {
        return ((root, query, builder) ->
                builder.greaterThanOrEqualTo(root.get("dataInserimento"), date));
    }

    public static Specification<Clienti> dataUltimoContrattoMaggioreDi(LocalDate date) {
        return ((root, query, builder) ->
                builder.greaterThan(root.get("dataUltimoContratto"), date));
    }

    public static Specification<Clienti> findByCognomeContratto(String cognome) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("cognome")), "%" + cognome.toLowerCase() + "%");
    }

    public static Specification<Clienti> findByNomeContatto(String nome) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }


}
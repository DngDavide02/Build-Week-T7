package Team7.Build_Week_T7.specification;

import Team7.Build_Week_T7.entities.Fatture;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FattureSpec {
    public static Specification<Fatture> dataEqual(LocalDate data) {
        return (root, query, builder) ->
                builder.equal(root.get("data"), data);
    }

    public static Specification<Fatture> clienteEqual(String ragioneSociale) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("clienti").get("ragioneSociale")),
                        "%" + ragioneSociale.toLowerCase() + "%");
    }

    public static Specification<Fatture> statoEqual(String stato) {
        return (root, query, builder) ->
                builder.equal(root.get("statoFatture").get("stato"), stato);
    }

    public static Specification<Fatture> importoRange(Integer min, Integer max) {
        return (root, query, builder) -> {
            if (min != null && max != null) {
                return builder.between(root.get("importo"), min, max);
            } else if (min != null) {
                return builder.greaterThanOrEqualTo(root.get("importo"), min);
            } else if (max != null) {
                return builder.lessThanOrEqualTo(root.get("importo"), max);
            }
            return null;
        };
    }

    public static Specification<Fatture> annoEqual(LocalDate data) {
        return (root, query, builder) ->
                builder.equal(builder.function("YEAR", Integer.class, root.get("data")),
                        data.getYear());
    }
}


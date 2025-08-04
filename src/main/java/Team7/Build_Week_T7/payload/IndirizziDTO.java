package Team7.Build_Week_T7.payload;

public record IndirizziDTO(
        Long id,
        String via,
        int civico,
        String localita,
        int cap,
        Long comuneId
) {}

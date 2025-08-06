package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ProvinciaDTO;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ProvinciaCSVService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaCSVService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public void importaCSVProvincia() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("province-italiane.csv");
            if (inputStream == null) {
                throw new NotFoundException("File CSV provincie-italiane.csv non trovato!");
            }
            InputStreamReader reader = new InputStreamReader(inputStream);

            List<ProvinciaDTO> provinciaDTOList = new CsvToBeanBuilder<ProvinciaDTO>(reader)
                    .withType(ProvinciaDTO.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .build()
                    .parse();

            for (ProvinciaDTO provinciaDTO : provinciaDTOList) {
                String nomeProvinciaNormalizzato = provinciaDTO.getProvincia();

                if (!provinciaRepository.existsByProvinciaIgnoreCase(nomeProvinciaNormalizzato)) {
                    Provincia provincia = new Provincia();
                    provincia.setSigla(provinciaDTO.getSigla().trim());
                    switch (nomeProvinciaNormalizzato.toLowerCase()) {
                        case "verbania" -> provincia.setProvincia("Verbano Cusio Ossola");
                        case "aosta" -> provincia.setProvincia("Valle d'Aosta Vallée d'Aoste");
                        case "monza-brianza" -> provincia.setProvincia("Monza e della Brianza");
                        case "bolzano" -> provincia.setProvincia("Bolzano Bozen");
                        case "la-spezia" -> provincia.setProvincia("La Spezia");
                        case "reggio-emilia" -> provincia.setProvincia("Reggio nell'Emilia");
                        case "forli-cesena" -> provincia.setProvincia("Forlì Cesena");
                        case "massa-carrara" -> provincia.setProvincia("Massa Carrara");
                        case "pesaro-urbino" -> provincia.setProvincia("Pesaro e Urbino");
                        case "ascoli-piceno" -> provincia.setProvincia("Ascoli Piceno");
                        case "barletta-andria-trani" -> provincia.setProvincia("Barletta Andria Trani");
                        case "reggio-calabria" -> provincia.setProvincia("Reggio Calabria");
                        case "vibo-valentia" -> provincia.setProvincia("Vibo Valentia");
                        case "cagliari" -> provincia.setProvincia("Sud Sardegna");
                        default -> provincia.setProvincia(nomeProvinciaNormalizzato);
                    }

                    provincia.setRegione(provinciaDTO.getRegione().trim());
                    provinciaRepository.save(provincia);
                }
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("CSV non trovato");
        }
    }

    private String normalizzaNome(String nome) {
        return nome
                .replace("-", " ")
                .replace("/", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

}

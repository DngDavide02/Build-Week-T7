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
                    switch (nomeProvinciaNormalizzato) {
                        case "Verbania" -> provincia.setProvincia("Verbano Cusio Ossola");
                        case "Valle d'Aosta" -> provincia.setProvincia("Valle d'Aosta Vallée d'Aoste");
                        case "Monza-Brianza" -> provincia.setProvincia("Monza e della Brianza");
                        case "Bolzano" -> provincia.setProvincia("Bolzano Bozen");
                        case "La-Spezia" -> provincia.setProvincia("La Spezia");
                        case "Reggio-Emilia" -> provincia.setProvincia("Reggio nell'Emilia");
                        case "Forli-Cesena" -> provincia.setProvincia("Forlì Cesena");
                        case "Massa-Carrara" -> provincia.setProvincia("Massa Carrara");
                        case "Pesaro-Urbino" -> provincia.setProvincia("Pesaro e Urbino");
                        case "Ascoli-Piceno" -> provincia.setProvincia("Ascoli Piceno");
                        case "Barletta-Andria-Trani" -> provincia.setProvincia("Barletta Andria Trani");
                        case "Reggio-Calabria" -> provincia.setProvincia("Reggio Calabria");
                        case "Vibo-Valentia" -> provincia.setProvincia("Vibo Valentia");
                        case "Cagliari" -> provincia.setProvincia("Sud Sardegna");
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

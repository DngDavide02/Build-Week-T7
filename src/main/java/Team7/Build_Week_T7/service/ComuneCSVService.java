package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ComuneDTO;
import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@Service
public class ComuneCSVService {

    private final ComuneRepository comuneRepository;
    private final ProvinciaRepository provinciaRepository;

    public ComuneCSVService(ComuneRepository comuneRepository, ProvinciaRepository provinciaRepository) {
        this.comuneRepository = comuneRepository;
        this.provinciaRepository = provinciaRepository;
    }

    public void importaCSVComuni() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("comuni-italiani.csv");

        if (inputStream == null) {
            throw new NotFoundException("File comuni-italiani.csv non trovato");
        }

        InputStreamReader reader = new InputStreamReader(inputStream);

        List<ComuneDTO> comuneDTOList = new CsvToBeanBuilder<ComuneDTO>(reader)
                .withType(ComuneDTO.class)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .build()
                .parse();

        for (ComuneDTO comuneDTO : comuneDTOList) {
            if (!comuneRepository.existsByDenominazione(comuneDTO.getDenominazione())) {
                String nomeProvinciaNormalizzato = normalizzaNome(comuneDTO.getProvincia());

                Optional<Provincia> provinciaOpt = provinciaRepository.findByProvinciaIgnoreCase(nomeProvinciaNormalizzato);

                Provincia provincia = null;
                switch (provinciaOpt.toString()){
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
                }
                
                Comune comune = new Comune();
                comune.setProgressivoComune(comuneDTO.getProgressivoComune().trim());
                comune.setDenominazione(comuneDTO.getDenominazione().trim());
                comune.setProvincia(provincia);
                comune.setCodiceProvincia(comuneDTO.getCodiceProvincia().trim());
                comuneRepository.save(comune);
            }
        }
    }

    private String normalizzaNome(String nome) {
        return nome
                .replace("-", " ")
                .replace("/", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public Comune findById(Long comuneId) {
        return this.comuneRepository.findById(comuneId).orElseThrow(() -> new NotFoundException(comuneId));
    }

    @PostConstruct
    public void init() {
        if (comuneRepository.count() == 0) {
            importaCSVComuni();
        } else {
            System.out.println("Comuni già presenti.");
        }
    }
}

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

                Provincia provincia;
                if (provinciaOpt.isEmpty()) {
                    provincia = new Provincia();
                    provincia.setSigla("ND");
                    provincia.setProvincia(nomeProvinciaNormalizzato);
                    provincia.setRegione("ND");
                    provinciaRepository.save(provincia);
                    System.out.println("Provincia creata dinamicamente: " + nomeProvinciaNormalizzato);
                } else {
                    provincia = provinciaOpt.get();
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

    @PostConstruct
    public void init() {
        if (comuneRepository.count() == 0) {
            importaCSVComuni();
        } else {
            System.out.println("Comuni già presenti.");
        }
    }
}

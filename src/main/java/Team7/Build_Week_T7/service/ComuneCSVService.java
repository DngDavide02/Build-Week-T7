package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ComuneDTO;
import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

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
            if (!comuneRepository.existsByDenominazione(comuneDTO.denominazione())) {
                Provincia provincia = provinciaRepository.findByProvincia(comuneDTO.provincia())
                        .orElseThrow(() -> new NotFoundException("Provincia non trovata" + comuneDTO.provincia()));

                Comune comune = new Comune();
                comune.setProgressivoComune(comuneDTO.progressivoComune());
                comune.setDenominazione(comuneDTO.denominazione());
                comune.setProvincia(provincia);
                comune.setCodiceProvincia(comuneDTO.codiceProvincia());
                comuneRepository.save(comune);
            }
        }
    }
}

package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ComuneDTO;
import Team7.Build_Week_T7.payload.ProvinciaDTO;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@Service
public class ProvinciaCSVService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaCSVService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public void importaCSVProvincia() {
        try {
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("provincie-italiane.csv")));

            List<ProvinciaDTO> provinciaDTOList = new CsvToBeanBuilder<ProvinciaDTO>(reader)
                    .withType(ProvinciaDTO.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(1)
                    .build()
                    .parse();

            for (ProvinciaDTO provinciaDTO : provinciaDTOList) {
                if (provinciaRepository.existsByProvincia(provinciaDTO.provincia()).isPresent()) {
                    Provincia provincia = new Provincia();
                    provincia.setSigla(provinciaDTO.sigla());
                    provincia.setProvincia(provinciaDTO.provincia());
                    provincia.setRegione(provinciaDTO.regione());
                    provinciaRepository.save(provincia);
                }
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("CSV non trovato");
        }
    }
}
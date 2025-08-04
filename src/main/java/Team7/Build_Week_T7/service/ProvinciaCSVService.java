package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ComuneDTO;
import Team7.Build_Week_T7.payload.ProvinciaDTO;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
                if (!provinciaRepository.existsByProvinciaIgnoreCase(provinciaDTO.getProvincia().trim())) {
                    Provincia provincia = new Provincia();
                    provincia.setSigla(provinciaDTO.getSigla().trim());
                    provincia.setProvincia(provinciaDTO.getProvincia().trim());
                    provincia.setRegione(provinciaDTO.getRegione().trim());
                    provinciaRepository.save(provincia);
                }
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("CSV non trovato");
        }
    }

    @PostConstruct
    public void init() {
        if (provinciaRepository.count() == 0) {
            importaCSVProvincia();
        } else {
            System.out.println("provincie già presenti.");
        }
    }
}
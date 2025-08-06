package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.entities.Provincia;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.ComuneDTO;
import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ComuneCSVService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

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


                Provincia provincia = this.findByProvinciaIgnoreCase(nomeProvinciaNormalizzato);

                Comune comune = new Comune();
                comune.setProgressivoComune(comuneDTO.getProgressivoComune().trim());
                comune.setDenominazione(comuneDTO.getDenominazione().trim());
                comune.setProvincia(provincia);
                comune.setCodiceProvincia(comuneDTO.getCodiceProvincia().trim());
                comuneRepository.save(comune);
            }
        }
    }

    public Provincia findByProvinciaIgnoreCase(String nomeProvincia) {
        return provinciaRepository.findByProvinciaIgnoreCase(nomeProvincia)
                .orElseThrow(() -> new NotFoundException("Provincia not found"));
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

}

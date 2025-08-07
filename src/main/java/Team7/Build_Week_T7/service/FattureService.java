package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.entities.StatoFatture;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.FattureUpdateDTO;
import Team7.Build_Week_T7.repository.FattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FattureService {
    @Autowired
    private FattureRepository fattureRepository;


    public List<Fatture> findAllFatture() {
        return fattureRepository.findAll();
    }

    public Optional<Fatture> findById(Long id) {
        return fattureRepository.findById(id);
    }


    public Fatture save(Fatture fattura) {
        return fattureRepository.save(fattura);
    }

    public Fatture findByIdAndUpdate(Long id, FattureUpdateDTO updateDTO) {
        Fatture esistente = this.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        if (updateDTO.data() != null) {
            esistente.setData(updateDTO.data());
        }
        if (updateDTO.importo() != null) {
            esistente.setImporto(updateDTO.importo());
        }
        if (updateDTO.numero() != null) {
            esistente.setNumero(updateDTO.numero());
        }

        return fattureRepository.save(esistente);
    }

    public void findByIdAndDelete(Long id) {
        Fatture found = this.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        fattureRepository.delete(found);
    }

    public List<Fatture> findByClienti(Clienti cliente) {
        return fattureRepository.findByClienti(cliente);
    }

    public List<Fatture> findByStatoFatture(StatoFatture statoFatture) {
        return fattureRepository.findByStatoFatture(statoFatture);
    }

    public List<Fatture> findByDataGreaterThan(LocalDate min) {
        return fattureRepository.findByDataGreaterThan(min);
    }

    public List<Fatture> findByDataBetween(int anno) {
        return fattureRepository.findByDataBetween(LocalDate.of(anno, 1, 1), LocalDate.of(anno, 12, 31));
    }

    public List<Fatture> findByImportoBetween(int min, int max) {
        return fattureRepository.findByImportoBetween(min, max);
    }

}

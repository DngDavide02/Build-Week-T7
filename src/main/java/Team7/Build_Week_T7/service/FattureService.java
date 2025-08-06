package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.FattureUpdateDTO;
import Team7.Build_Week_T7.repository.FattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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




}

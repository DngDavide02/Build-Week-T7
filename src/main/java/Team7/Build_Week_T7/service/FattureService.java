package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.Clienti;
import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.entities.StatoFatture;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.FattureUpdateDTO;
import Team7.Build_Week_T7.repository.FattureRepository;
import Team7.Build_Week_T7.repository.StatoFattureRepository;
import Team7.Build_Week_T7.specification.FattureSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FattureService {
    @Autowired
    private FattureRepository fattureRepository;

    @Autowired
    private StatoFattureRepository statoFattureRepository;


    public List<Fatture> findAllFatture() {
        return fattureRepository.findAll();
    }

    public Optional<Fatture> findById(Long id) {
        return fattureRepository.findById(id);
    }


    public Fatture save(Fatture fattura) {
        if (fattura.getStatoFatture() == null) {
            StatoFatture statoDaPagare = statoFattureRepository.findByStato("DA_PAGARE")
                    .orElseThrow(() -> new NotFoundException("Stato 'DA_PAGARE' non trovato nel sistema"));
            fattura.setStatoFatture(statoDaPagare);
        }
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

    public List<Fatture> searchFatture(
            String cliente,
            String stato,
            LocalDate data,
            Integer anno,
            Integer importoMin,
            Integer importoMax,
            String sortBy
    ) {
        Specification<Fatture> specification = (root, query, cb) -> cb.conjunction();

        if (cliente != null && !cliente.trim().isEmpty()) {
            specification = specification.and(FattureSpec.clienteEqual(cliente));
        }

        if (stato != null && !stato.trim().isEmpty()) {
            specification = specification.and(FattureSpec.statoEqual(stato));
        }

        if (data != null) {
            specification = specification.and(FattureSpec.dataEqual(data));
        }

        if (anno != null) {
            specification = specification.and(FattureSpec.annoEqual(LocalDate.of(anno, 1, 1)));
        }

        if (importoMin != null || importoMax != null) {
            specification = specification.and(FattureSpec.importoRange(importoMin, importoMax));
        }

        Sort sort = Sort.unsorted();
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "data" -> sort = Sort.by("data").ascending();
                case "cliente" -> sort = Sort.by("clienti.ragioneSociale").ascending();
                case "stato" -> sort = Sort.by("statoFatture.stato").ascending();
                case "importo" -> sort = Sort.by("importo").ascending();
            }
        }

        return fattureRepository.findAll(specification, sort);
    }

}

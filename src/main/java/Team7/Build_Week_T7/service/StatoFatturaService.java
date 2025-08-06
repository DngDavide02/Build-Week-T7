package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.StatoFatture;
import Team7.Build_Week_T7.payload.StatoFattureDTO;
import Team7.Build_Week_T7.repository.StatoFattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatoFatturaService {

    @Autowired
    private StatoFattureRepository statoFattureRepository;

    public StatoFatture creaNuovoStato(StatoFattureDTO dto) {
        if (statoFattureRepository.existsByStato(dto.stato())) {
            throw new IllegalArgumentException("Stato già esistente: " + dto.stato());
        }

        if (dto.stato() == null || dto.stato().trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome dello stato non può essere vuoto");
        }
        StatoFatture nuovoStato = new StatoFatture(
                dto.stato().trim().toUpperCase(),
                dto.descrizione()
        );

        return statoFattureRepository.save(nuovoStato);
    }

    public StatoFatture aggiornaStato(Long id, StatoFattureDTO dto) {
        StatoFatture stato = statoFattureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stato non trovato"));

        if (dto.stato() != null && !dto.stato().trim().isEmpty()) {
            if (!stato.getStato().equals(dto.stato()) &&
                    statoFattureRepository.existsByStato(dto.stato())) {
                throw new IllegalArgumentException("Nuovo nome stato già esistente");
            }
            stato.setStato(dto.stato().trim().toUpperCase());
        }

        if (dto.descrizione() != null) {
            stato.setDescrizione(dto.descrizione());
        }

        return statoFattureRepository.save(stato);
    }

    public void disattivaStato(Long id) {
        StatoFatture stato = statoFattureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stato non trovato"));
        stato.setAttivo(false);
        statoFattureRepository.save(stato);
    }

    public List<StatoFatture> getAllStati() {
        return statoFattureRepository.findAll();
    }

}

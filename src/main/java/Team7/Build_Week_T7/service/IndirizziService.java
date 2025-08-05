package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Indirizzi;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.IndirizziDTO;
import Team7.Build_Week_T7.repository.IndirizziRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IndirizziService {
    @Autowired
    private IndirizziRepository indirizziRepository;

    @Autowired
    private ComuneCSVService comuneService;

    public Indirizzi findById(Long indirizzoId) {
        return this.indirizziRepository.findById(indirizzoId).orElseThrow(() -> new NotFoundException(indirizzoId));
    }

    public Indirizzi save(IndirizziDTO payload) {
        Indirizzi newIndirizzi = new Indirizzi(payload.via(), payload.civico(), payload.localita(), payload.cap(), comuneService.findById(payload.comuneId()));
        Indirizzi savedIndirizzi = this.indirizziRepository.save(newIndirizzi);
        log.info("L'indirizzo con id: " + savedIndirizzi.getId() + " è stato salvato correttamente!");
        return savedIndirizzi;
    }

    public void findByIdAndDelete(Long indirizzoId) {
        Indirizzi found = this.findById(indirizzoId);
        this.indirizziRepository.delete(found);
    }


    public Indirizzi findByIdAndUpdate(Long indirizzoId, IndirizziDTO payload) {
        Indirizzi found = this.findById(indirizzoId);
        found.setVia(payload.via());
        found.setCap(payload.civico());
        found.setLocalita(payload.localita());
        found.setCap(payload.cap());
        found.setComune(comuneService.findById(payload.comuneId()));
        Indirizzi modifiedIndirizzi = this.indirizziRepository.save(found);
        log.info("L'evento con id " + found.getId() + " è stato modificato!");
        return modifiedIndirizzi;
    }
}


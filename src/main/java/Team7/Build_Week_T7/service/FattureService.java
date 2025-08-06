package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.Fatture;
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

    // Recupera tutte le fatture
    public List<Fatture> findAllFatture() {
        return fattureRepository.findAll();
    }

    // Recupera fatture con paginazione
    public Page<Fatture> getFatturePaginate(Pageable pageable) {
        return fattureRepository.findAll(pageable);
    }

    // Trova fattura per ID
    public Optional<Fatture> findById(Long id) {
        return fattureRepository.findById(id);
    }

    // Salva una nuova fattura
    public Fatture save(Fatture fattura) {
        return fattureRepository.save(fattura);
    }

    // Elimina una fattura per ID
    public void deleteById(Long id) {
        fattureRepository.deleteById(id);
    }

    // Aggiorna una fattura esistente
    public Fatture updateFattura(Long id, Fatture fattura) {
        if (fattureRepository.existsById(id)) {
            fattura.setId(id);
            return fattureRepository.save(fattura);
        }
        throw new IllegalArgumentException("Fattura con ID " + id + " non trovata");
    }

    // Verifica se esiste una fattura con un determinato ID
    public boolean existsById(Long id) {
        return fattureRepository.existsById(id);
    }

    // Conta il numero totale di fatture
    public long countFatture() {
        return fattureRepository.count();
    }




}

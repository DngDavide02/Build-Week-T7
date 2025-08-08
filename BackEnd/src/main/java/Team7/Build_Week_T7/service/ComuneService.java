package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Comune;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;

    public Comune findById(Long id) {
        return comuneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comune con id " + id + " non trovato"));
    }
}
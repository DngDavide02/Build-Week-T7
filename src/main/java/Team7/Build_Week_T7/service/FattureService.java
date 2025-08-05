package Team7.Build_Week_T7.service;


import Team7.Build_Week_T7.entities.Fatture;
import Team7.Build_Week_T7.repository.FattureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FattureService {
    @Autowired
    private FattureRepository fattureRepository;


    public List<Fatture> findAllFatture(){
        return fattureRepository.findAll();
    }

    public Optional<Fatture> findById(Long id){
        return fattureRepository.findById(id);
    }


    public Fatture save(Fatture fattura){
        return fattureRepository.save(fattura);
    }
}

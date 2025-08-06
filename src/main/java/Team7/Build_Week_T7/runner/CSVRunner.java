package Team7.Build_Week_T7.runner;

import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import Team7.Build_Week_T7.service.ComuneCSVService;
import Team7.Build_Week_T7.service.ProvinciaCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CSVRunner implements CommandLineRunner {

    @Autowired
    private ProvinciaCSVService provinciaCSVService;
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComuneCSVService comuneCSVService;
    @Autowired
    private ComuneRepository comuneRepository;

    @Override
    public void run(String... args) throws Exception {
        if (provinciaRepository.count() == 0) {
            provinciaCSVService.importaCSVProvincia();
        } else {
            System.out.println("Province già presenti.");
        }

        if (comuneRepository.count() == 0) {
            comuneCSVService.importaCSVComuni();
        } else {
            System.out.println("Comuni già presenti.");
        }
    }
}



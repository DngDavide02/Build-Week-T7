package Team7.Build_Week_T7.runner;

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
    private ComuneCSVService comuneCSVService;

    @Override
    public void run(String... args) throws Exception {
    }
}

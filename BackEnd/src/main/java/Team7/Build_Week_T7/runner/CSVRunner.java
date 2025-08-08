package Team7.Build_Week_T7.runner;

import Team7.Build_Week_T7.entities.StatoFatture;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.repository.*;
import Team7.Build_Week_T7.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CSVRunner implements CommandLineRunner {

    @Value("${admin_password}")
    String adPassword;
    @Autowired
    private ProvinciaCSVService provinciaCSVService;
    @Autowired
    private ComuneCSVService comuneCSVService;
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private ComuneRepository comuneRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private StatoFattureRepository statoFattureRepository;
    @Autowired
    private StatoFatturaService statoFatturaService;

    @Override
    public void run(String... args) {
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
        if (roleRepository.count() == 0) {
            roleService.createRole("admin");
            roleService.createRole("user");
        } else System.out.println("Ruoli già presenti.");

        if(statoFattureRepository.count() == 0) {
           statoFattureRepository.save(new StatoFatture("DA_PAGARE", "fatture da pagare"));
            statoFattureRepository.save(new StatoFatture("PAGATE", "fatture pagate"));
        }else System.out.println("stati gia presenti.");

        if (userRepository.count() == 0) {
            String encodedPassword = passwordEncoder.encode(adPassword);
            User admin = new User(
                    "admin",
                    "admin@mail.com",
                    encodedPassword,
                    "admin",
                    "admin",
                    "https://ui-avatars.com/api/?name=admin+admin"
            );
            userRepository.save(admin);
            userRoleService.save(admin, roleService.getRoleByName("ADMIN"));
        } else System.out.println("Admin già presente");
    }
}

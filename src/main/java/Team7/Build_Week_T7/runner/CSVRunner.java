package Team7.Build_Week_T7.runner;

import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.repository.ComuneRepository;
import Team7.Build_Week_T7.repository.ProvinciaRepository;
import Team7.Build_Week_T7.repository.RoleRepository;
import Team7.Build_Week_T7.repository.UserRepository;
import Team7.Build_Week_T7.service.ComuneCSVService;
import Team7.Build_Week_T7.service.ProvinciaCSVService;
import Team7.Build_Week_T7.service.RoleService;
import Team7.Build_Week_T7.service.UserRoleService;
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

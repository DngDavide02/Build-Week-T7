package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.entities.UserRole;
import Team7.Build_Week_T7.exception.BadRequestException;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.UserRegistrationDTO;
import Team7.Build_Week_T7.repository.RoleRepository;
import Team7.Build_Week_T7.repository.UserRepository;
import Team7.Build_Week_T7.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE
    public User save(UserRegistrationDTO dto) {
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new BadRequestException("Email '" + u.getEmail() + "' already used");
        });

        userRepository.findByUsername(dto.username()).ifPresent(u -> {
            throw new BadRequestException("Username '" + u.getUsername() + "' already taken");
        });

        String encodedPassword = passwordEncoder.encode(dto.password());

        User user = new User(
                dto.username(),
                dto.email(),
                encodedPassword,
                dto.nome(),
                dto.cognome(),
                "https://ui-avatars.com/api/?name=" + dto.nome() + "+" + dto.cognome()
        );

        User savedUser = userRepository.save(user);

        Role role = roleRepository.findByName(dto.role().toUpperCase())
                .orElseThrow(() -> new BadRequestException("Role '" + dto.role() + "' not found"));

        userRoleRepository.save(new UserRole(savedUser, role));

        return savedUser;
    }

    // READ
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email '" + email + "' not found"));
    }

    // UPDATE
    public User findByIdAndUpdate(Long userId, UserRegistrationDTO dto) {
        User user = findById(userId);

        if (!user.getEmail().equals(dto.email())) {
            userRepository.findByEmail(dto.email()).ifPresent(u -> {
                throw new BadRequestException("Email '" + dto.email() + "' already used");
            });
        }

        user.setUsername(dto.username());
        user.setNome(dto.nome());
        user.setCognome(dto.cognome());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setAvatar("https://ui-avatars.com/api/?name=" + dto.nome() + "+" + dto.cognome());

        return userRepository.save(user);
    }

    // DELETE
    public void findByIdAndDelete(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }


}
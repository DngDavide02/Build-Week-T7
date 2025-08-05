package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.exception.ValidationException;
import Team7.Build_Week_T7.payload.UserRegistrationDTO;
import Team7.Build_Week_T7.payload.UserRespDTO;
import Team7.Build_Week_T7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET all users - only for ADMIN
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> findAll() {
        return userService.findAll();
    }

    // POST registration - only for ADMIN
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserRespDTO createUser(@RequestBody @Validated UserRegistrationDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        User newUser = userService.save(payload);
        return new UserRespDTO(newUser.getId());
    }

    // GET user by ID - only for ADMIN
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    // PUT user by ID - only for ADMIN
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@PathVariable Long userId,
                           @RequestBody @Validated UserRegistrationDTO payload,
                           BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return userService.findByIdAndUpdate(userId, payload);
    }

    // DELETE user by ID - only for ADMIN
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.findByIdAndDelete(userId);
    }

    // GET my profile
    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User authenticatedUser) {
        return authenticatedUser;
    }

    // PUT my profile
    @PutMapping("/me")
    public User updateMyProfile(@AuthenticationPrincipal User authenticatedUser,
                                @RequestBody @Validated UserRegistrationDTO payload) {
        return userService.findByIdAndUpdate(authenticatedUser.getId(), payload);
    }

}

package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.exception.ValidationException;
import Team7.Build_Week_T7.payload.*;
import Team7.Build_Week_T7.service.AuthorizationService;
import Team7.Build_Week_T7.service.RoleService;
import Team7.Build_Week_T7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authorizationService.checkCredentialsAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }


    //TODO!! VA TOLTO ALTRIMENTI SI REGISTRA CHIUNQUE

    // POST /users
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO createUser(@RequestBody @Validated UserRegistrationDTO dto, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        User createdUser = userService.save(dto);
        return new UserRespDTO(createdUser.getId());
    }

    // POST /roles - crea un nuovo ruolo (solo ADMIN)
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@RequestBody @Validated RoleDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }
        return roleService.createRole(payload.name());
    }


}

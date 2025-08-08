package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.payload.LoginDTO;
import Team7.Build_Week_T7.payload.LoginRespDTO;
import Team7.Build_Week_T7.service.AuthorizationService;
import Team7.Build_Week_T7.service.RoleService;
import Team7.Build_Week_T7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    /*
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
*/

}

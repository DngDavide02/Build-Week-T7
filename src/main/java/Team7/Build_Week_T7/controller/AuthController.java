package Team7.Build_Week_T7.controller;


import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.payload.LoginDTO;
import Team7.Build_Week_T7.payload.LoginRespDTO;
import Team7.Build_Week_T7.payload.UserRegistrationDTO;
import Team7.Build_Week_T7.payload.UserRespDTO;
import Team7.Build_Week_T7.service.AuthorizationService;
import Team7.Build_Week_T7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authorizationService.checkCredentialsAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }


    //TODO!! VA TOLTO ALTRIMENTI SI REGISTRA CHIUNQUE

    // POST /users
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO createUser(@RequestBody @Validated UserRegistrationDTO dto) {
        User createdUser = userService.save(dto);
        return new UserRespDTO(createdUser.getId());
    }


}

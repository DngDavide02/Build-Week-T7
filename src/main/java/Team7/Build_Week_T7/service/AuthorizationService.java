package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.exception.UnauthorizedException;
import Team7.Build_Week_T7.payload.LoginDTO;
import Team7.Build_Week_T7.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;


    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        User found = this.userService.findByEmail(body.email());


        if (bcrypt.matches(body.password(), found.getPassword())) {

            String accessToken = jwtTools.createToken(found);

            return accessToken;
        } else {
            throw new UnauthorizedException("Incorrect credentials!");
        }
    }


}
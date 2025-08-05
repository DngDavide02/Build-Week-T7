package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.payload.UserRegistrationDTO;
import Team7.Build_Week_T7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET /users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }


    // PUT /users/{id}
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Validated UserRegistrationDTO dto) {
        return userService.findByIdAndUpdate(id, dto);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.findByIdAndDelete(id);
    }
}

package Team7.Build_Week_T7.controller;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.UserRole;
import Team7.Build_Week_T7.payload.RoleDTO;
import Team7.Build_Week_T7.payload.UserRoleDTO;
import Team7.Build_Week_T7.service.RoleService;
import Team7.Build_Week_T7.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    // POST /roles - crea un nuovo ruolo (solo ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@RequestBody @Valid RoleDTO payload) {
        return roleService.createRole(payload.name());
    }

    // GET /roles - ottiene tutti i ruoli (solo ADMIN)
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // GET /roles/{name} - ottiene un ruolo per nome (solo ADMIN)
    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    //UPDATE USER ROLE

    @PutMapping("/user/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserRole updateRole(@RequestBody @Validated UserRoleDTO payload) {
        return this.userRoleService.findByIdAndUpdate(payload.userId(), payload);

    }


}


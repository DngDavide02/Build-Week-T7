package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name) {
        return roleRepository.save(new Role(name.toUpperCase()));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Role not found"));
    }

    public boolean roleExist(String name) {
        return roleRepository.findByName(name.toUpperCase()).isPresent();
    }
}


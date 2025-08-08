package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.entities.UserRole;
import Team7.Build_Week_T7.exception.BadRequestException;
import Team7.Build_Week_T7.exception.NotFoundException;
import Team7.Build_Week_T7.payload.UserRoleDTO;
import Team7.Build_Week_T7.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleService roleService;

    public List<UserRole> getRolesByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    public UserRole save(User user, Role role) {
        if (this.userRoleRepository.findByRoleAndUser(role, user).isEmpty())
            return this.userRoleRepository.save(new UserRole(user, role));
        else throw new BadRequestException("Lo user possiede già questo ruolo");
    }

    public void removeAllRolesFromUser(User user) {
        List<UserRole> roles = userRoleRepository.findByUser(user);
        userRoleRepository.deleteAll(roles);
    }

    public UserRole findByid(Long userRoleId) {
        return this.userRoleRepository.findById(userRoleId).orElseThrow(() -> new NotFoundException(userRoleId));
    }

    public UserRole findByIdAndUpdate(long userRoleId, UserRoleDTO payload) {
        UserRole found = this.findByid(userRoleId);
        found.setRole(payload.role());
        UserRole modifiedUserRole = this.userRoleRepository.save(found);
        return modifiedUserRole;
    }

    public UserRole findByIdAndUpdate(long userRoleId, String role) {
        UserRole found = this.findByid(userRoleId);
        Role fndRole = roleService.getRoleByName(role);
        found.setRole(fndRole);
        return this.userRoleRepository.save(found);
    }

}


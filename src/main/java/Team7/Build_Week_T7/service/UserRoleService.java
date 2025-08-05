package Team7.Build_Week_T7.service;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.entities.UserRole;
import Team7.Build_Week_T7.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getRolesByUser(User user) {
        return userRoleRepository.findByUser(user);
    }

    public void assignRoleToUser(User user, Role role) {
        UserRole userRole = new UserRole(user, role);
        userRoleRepository.save(userRole);
    }

    public void removeAllRolesFromUser(User user) {
        List<UserRole> roles = userRoleRepository.findByUser(user);
        userRoleRepository.deleteAll(roles);
    }
}


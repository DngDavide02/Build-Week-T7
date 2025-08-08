package Team7.Build_Week_T7.repository;

import Team7.Build_Week_T7.entities.Role;
import Team7.Build_Week_T7.entities.User;
import Team7.Build_Week_T7.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

    Optional<UserRole> findById(Long id);

    Optional<UserRole> findByRoleAndUser(Role role, User user);
}

package Team7.Build_Week_T7.payload;

import Team7.Build_Week_T7.entities.Role;

public record UserRoleDTO(
        Long userId,
        Role role

) {
}

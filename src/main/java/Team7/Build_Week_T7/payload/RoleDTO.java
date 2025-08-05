package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.NotEmpty;

public record RoleDTO(
        @NotEmpty(message = "Role name is required")
        String name
) {
}
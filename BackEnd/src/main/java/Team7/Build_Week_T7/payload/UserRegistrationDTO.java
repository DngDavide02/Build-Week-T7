package Team7.Build_Week_T7.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(

        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "First name is required")
        @Size(max = 50, message = "First name can't be longer than 50 characters")
        String nome,

        @NotEmpty(message = "Last name is required")
        @Size(max = 50, message = "Last name can't be longer than 50 characters")
        String cognome,

        @NotEmpty(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String role,

        @NotEmpty(message = "Password is required")
        String password

) {
}

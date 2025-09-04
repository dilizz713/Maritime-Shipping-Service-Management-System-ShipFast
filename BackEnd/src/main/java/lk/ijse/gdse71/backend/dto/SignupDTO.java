package lk.ijse.gdse71.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "User name is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    private String role;

}

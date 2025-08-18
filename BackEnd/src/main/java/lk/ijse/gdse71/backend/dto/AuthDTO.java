package lk.ijse.gdse71.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotNull(message = "User Name is required")
    public String username;

    @NotNull(message = "Password is required")
    public String password;
}

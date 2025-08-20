package lk.ijse.gdse71.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDTO {
    private Long id;

    @NotNull(message = "Service type is required")
    private String serviceType;

    private String description;
}

package lk.ijse.gdse71.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDTO {
    private Long id;

    @NotBlank(message = "Service type is required")
    private String serviceName;
    private String description;
}

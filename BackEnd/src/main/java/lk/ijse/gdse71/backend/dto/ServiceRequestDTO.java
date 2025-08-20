package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceRequestDTO {
    private Long id;
    private Date requestingDate;
    private Long customerId;
    private Long serviceId;
    private String description;
}

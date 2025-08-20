package lk.ijse.gdse71.backend.dto;

import lk.ijse.gdse71.backend.entity.Services;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceRequestDTO {
    private Long id;
    private Date requestingDate;
    private Long customerId;
    private List<Long> serviceId;
    private Long portId;
    private String description;

}

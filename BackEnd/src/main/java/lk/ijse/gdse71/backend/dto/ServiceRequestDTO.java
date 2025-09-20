package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceRequestDTO {
    private Long id;
    private String shipName;
    private Date requestingDate;
    private Long customerId;
    private String customerName;
    private List<Long> serviceIds;
    private Long portId;
    private String description;
    private String status;

    private String portName;
    private List<String> serviceNames;


}

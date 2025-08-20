package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceRequestPortDTO {
    private Long id;
    private String specialInstructions;
    private Date scheduleDate;
    private Long portId;
    private Long requestId;
}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobScheduleDTO {
    private Long id;
    private Long jobId;
    private String status;
    private Double originalPO;
    private Double matchedPO;
    private String remarks;
}

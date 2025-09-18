package lk.ijse.gdse71.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobScheduleInfoDTO {
    private Long id;
    private String jobReference;

    private Date jobDate;
    private String customerName;
    private String vesselName;
    private String serviceName;
    private String portName;
    private Double originalPO;
    private Double matchedPO;
    private String status;
    private String employeeName;
    private String remark;
}

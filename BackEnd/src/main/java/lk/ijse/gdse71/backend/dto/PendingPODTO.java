package lk.ijse.gdse71.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingPODTO {
    private Long id;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    private Long jobId;
    private String jobReference;
    private String remark;
    private String status;

    private String customerName;
    private String vesselName;
    private String portName;
    private String serviceName;
    private String employeeName;
}

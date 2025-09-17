package lk.ijse.gdse71.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {
    private Long id;
    private String jobReference;
    private String remark;
    private String status;

    private Long customerId;
    private String customerName;

    private Long vesselId;
    private String vesselName;

    private Long portId;
    private String portName;

    private Long employeeId;
    private String employeeName;

    private Long serviceId;
    private String serviceName;

    private String referenceFilePath;
    private String dateAsString;

    private MultipartFile referenceFile;
}

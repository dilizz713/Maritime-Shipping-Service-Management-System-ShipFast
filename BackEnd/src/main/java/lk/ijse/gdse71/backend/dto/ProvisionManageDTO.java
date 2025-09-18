package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionManageDTO {
    private Long id;
    private String jobReference;
    private Long jobId;
    private Date date;
    private String remark;
    private Double qty;
    private Double totalAmount;
    private Double discount;
}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotationInfoDTO {
    private String jobReference;
    private String quotationNumber;
    private String employeeName;
    private String customerName;
    private String vesselName;
    private String quotationFile;
    private Date quotationDate;
}

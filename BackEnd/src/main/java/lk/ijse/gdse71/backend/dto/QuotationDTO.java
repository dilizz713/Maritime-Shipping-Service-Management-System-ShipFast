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
public class QuotationDTO {
    private Long id;
    private Long jobId;
    private String jobReference;
    private String quotationFile;
    private Date quotationDate;
    private String quotationNumber;


}

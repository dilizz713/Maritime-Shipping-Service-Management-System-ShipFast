package lk.ijse.gdse71.backend.dto;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmInquiryDTO {
    private Long id;
    private String billNumber;
    private String description;

    private Long inquiryId;
    private String referenceNumber;
    private String vendorName;
    private LocalDate confirmationDate;
}

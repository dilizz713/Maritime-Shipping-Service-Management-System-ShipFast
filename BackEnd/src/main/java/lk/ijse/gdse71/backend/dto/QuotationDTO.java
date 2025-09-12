package lk.ijse.gdse71.backend.dto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationDTO {
    private Long inquiryId;
    private String billNumber;
    private double totalAmount;
    private List<QuotationItemDTO> items;
}

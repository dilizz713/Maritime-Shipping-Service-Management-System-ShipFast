package lk.ijse.gdse71.backend.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GRNDTO {
    private Long id;
    private String grnNumber;
    private LocalDate receivedDate;
    private Double totalAmount;
    private String remark;

    private String billNumber;
    private String vendorName;

    private List<GRNItemDTO> items;
}

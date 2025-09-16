package lk.ijse.gdse71.backend.dto;
import lk.ijse.gdse71.backend.entity.ProductType;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GRNItemDTO {
    private Long id;

    private String productCode;
    private String productName;
    private ProductType productType;
    private String uom;

    private Integer qty;
    private Double unitPrice;
    private Double discount;
    private Double amount;

    private String description;
}

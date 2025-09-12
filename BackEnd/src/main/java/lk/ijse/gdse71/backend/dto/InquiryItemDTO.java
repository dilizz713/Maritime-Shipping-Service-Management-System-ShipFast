package lk.ijse.gdse71.backend.dto;

import lk.ijse.gdse71.backend.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryItemDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double totalAmount;
    private ProductStatus status;
    private String remarks;
}

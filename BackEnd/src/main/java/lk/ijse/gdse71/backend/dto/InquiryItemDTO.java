package lk.ijse.gdse71.backend.dto;

import lk.ijse.gdse71.backend.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryItemDTO {
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double discount;
    private Double totalAmount;
    private ProductStatus status;
}

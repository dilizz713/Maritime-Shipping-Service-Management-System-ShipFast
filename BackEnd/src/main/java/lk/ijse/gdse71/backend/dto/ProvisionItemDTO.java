package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionItemDTO {
    private Long productId;
    private String productCode;
    private String productName;
    private String uomCode;
    private Double unitPrice;
    private Integer quantity;
    private String remark;
}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionInvoiceItemDTO {
    private String name;
    private Integer quantity;
    private Double unitCost;


}

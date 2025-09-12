package lk.ijse.gdse71.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationItemDTO {
    private Long itemId;
    private double discount;
    private double amount;
}

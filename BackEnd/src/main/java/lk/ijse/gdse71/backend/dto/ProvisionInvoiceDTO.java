package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionInvoiceDTO {
    private String from;
    private String to;
    private String number;
    private String date;
    private String dueDate;
    private String notes;
    private String logoUrl;
    private String currency;
    private List<ProvisionInvoiceItemDTO> items;

    private Double totalAmount;
}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceInvoiceDTO {
    private String from;
    private String to;
    private String jobReference;
    private String date;
    private String dueDate;
    private String notes;
    private String logoUrl;
    private String currency;
    private Double totalAmount;
}

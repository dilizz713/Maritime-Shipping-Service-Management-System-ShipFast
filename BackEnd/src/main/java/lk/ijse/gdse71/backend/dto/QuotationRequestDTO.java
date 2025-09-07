package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuotationRequestDTO {
    private String companyName;
    private String email;
    private String harbour;
    private String position;
    private String service;
    private String message;
}

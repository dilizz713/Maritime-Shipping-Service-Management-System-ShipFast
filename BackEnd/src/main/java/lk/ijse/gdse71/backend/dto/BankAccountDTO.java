package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private Long id;
    private String bank;
    private String accountNumber;
    private String branch;
    private String swiftCode;
    private String currency;
    private Long vendorId;
}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private Long id;
    private String accountNumber;
    private String branch;
    private String currency;

    private Long vendorId;
    private Long bankId;
    private String bankName;

}

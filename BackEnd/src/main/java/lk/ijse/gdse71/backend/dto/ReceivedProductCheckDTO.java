package lk.ijse.gdse71.backend.dto;

import lombok.*;

@Data
@Builder
public class ReceivedProductCheckDTO {
    private Long itemId;
    private String productCode;
    private String productName;
    private int confirmedQty;
    private int receivedQty;
    private String description;
    private boolean correct;
}

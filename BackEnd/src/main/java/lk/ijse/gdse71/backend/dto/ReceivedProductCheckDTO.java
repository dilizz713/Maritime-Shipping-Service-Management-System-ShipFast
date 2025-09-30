package lk.ijse.gdse71.backend.dto;

import lombok.*;

@Data
@Builder
public class ReceivedProductCheckDTO {
    private Long id;
    private Long itemId;
    private String productCode;
    private String productName;
    private Integer confirmedQty;
    private Integer receivedQty;
    private Boolean correct;
    private String description;

}

package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VesselsDTO {
    private Long id;
    private String name;
    private double capacity;
    private String location;
    private String type;
    private Long customerId;
    private String customerName;
}

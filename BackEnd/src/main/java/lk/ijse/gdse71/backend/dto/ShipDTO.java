package lk.ijse.gdse71.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShipDTO {
    private String name;
    private Double capacity;
    private String location;   //country
    private String shipType;
}

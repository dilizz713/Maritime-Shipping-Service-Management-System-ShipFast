package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleLocationDTO {
    private Long vehicleId;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private String timestamp;

}

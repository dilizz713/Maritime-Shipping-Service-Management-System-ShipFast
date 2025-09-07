package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private Long id;
    private String name;
    private String numberPlate;
    private String type;
    private String model;
    private String status;
    private String image;
}

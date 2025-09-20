package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VehicleService {

    void updateVehicle(Long id, VehicleDTO vehicleDTO , MultipartFile file);

    void deleteVehicle(Long id);

    Object getVehicleById(Long id);

    List<VehicleDTO> getAllVehicles();

    void addVehicle(VehicleDTO dto, MultipartFile image);
}

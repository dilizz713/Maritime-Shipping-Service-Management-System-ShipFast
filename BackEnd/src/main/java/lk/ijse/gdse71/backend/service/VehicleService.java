package lk.ijse.gdse71.backend.service;

import aj.org.objectweb.asm.commons.Remapper;
import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VehicleService {

    void updateVehicle(Long id, VehicleDTO vehicleDTO , MultipartFile file);

    void deleteVehicle(Long id);

    Object getVehicleById(Long id);

    List<VehicleDTO> getAllVehicles();

    void addVehicle(VehicleDTO dto, MultipartFile image);
}

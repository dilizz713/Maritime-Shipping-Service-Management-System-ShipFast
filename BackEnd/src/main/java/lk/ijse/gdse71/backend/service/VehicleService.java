package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle addVehicle(VehicleDTO dto);

    VehicleLocation saveLocation(VehicleLocationDTO dto);

    Optional<VehicleLocation> getLatestLocation(Long id);

    List<VehicleLocation> getHistory(Long id, Instant parse, Instant parse1);

    List<Vehicle> getAllVehicles();
}

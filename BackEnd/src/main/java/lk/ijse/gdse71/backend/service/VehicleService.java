package lk.ijse.gdse71.backend.service;

import aj.org.objectweb.asm.commons.Remapper;
import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle addVehicle(VehicleDTO dto);

    List<Vehicle> getAllVehicles();

    void saveLocation(VehicleLocation location);

    Optional<VehicleLocation> getLatestLocation(Long vehicleId);

    List<VehicleLocation> getRecentLocations(Long vehicleId);

    Optional<Vehicle> getVehicleById(Long vehicleId);
}

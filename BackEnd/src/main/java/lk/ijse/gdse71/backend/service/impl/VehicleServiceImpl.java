package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;
import lk.ijse.gdse71.backend.repo.VehicleLocationRepository;
import lk.ijse.gdse71.backend.repo.VehicleRepository;
import lk.ijse.gdse71.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepo;
    private final VehicleLocationRepository locationRepo;


    @Override
    public Vehicle addVehicle(VehicleDTO dto) {
        Vehicle v = Vehicle.builder()
                .plateNumber(dto.getPlateNumber())
                .type(dto.getType())
                .model(dto.getModel())
                .build();
        return vehicleRepo.save(v);
    }



    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    @Override
    public void saveLocation(VehicleLocation location) {
        locationRepo.save(location);
    }

    @Override
    public Optional<VehicleLocation> getLatestLocation(Long vehicleId) {
        return locationRepo.findLatestByVehicleId(vehicleId);
    }

    @Override
    public List<VehicleLocation> getRecentLocations(Long vehicleId) {
        return locationRepo.findTop10ByVehicleIdOrderByTimestampDesc(vehicleId);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long vehicleId) {
        return vehicleRepo.findById(vehicleId);
    }
}

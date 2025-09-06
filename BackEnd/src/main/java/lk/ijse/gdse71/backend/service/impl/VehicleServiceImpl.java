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
    private final ModelMapper modelMapper;

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
    public VehicleLocation saveLocation(VehicleLocationDTO dto) {
        Vehicle vehicle = vehicleRepo.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        VehicleLocation loc = VehicleLocation.builder()
                .vehicle(vehicle)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .speed(dto.getSpeed())
                .timestamp(Instant.parse(dto.getTimestamp()))
                .build();

        return locationRepo.save(loc);
    }

    @Override
    public Optional<VehicleLocation> getLatestLocation(Long vehicleId) {
        return locationRepo.findLatestByVehicleId(vehicleId);
    }

    @Override
    public List<VehicleLocation> getHistory(Long id, Instant from, Instant to) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return locationRepo.findByVehicleAndTimestampBetween(vehicle, from, to);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }
}

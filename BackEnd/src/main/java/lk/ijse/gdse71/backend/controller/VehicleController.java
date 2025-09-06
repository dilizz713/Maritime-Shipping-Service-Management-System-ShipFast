package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;
import lk.ijse.gdse71.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("getAllVehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping
    public Vehicle addVehicle(@RequestBody VehicleDTO dto) {
        return vehicleService.addVehicle(dto);
    }

    @PostMapping("/location")
    public VehicleLocation addLocation(@RequestBody VehicleLocationDTO dto) {
        return vehicleService.saveLocation(dto);
    }

    @GetMapping("/{id}/location/latest")
    public ResponseEntity<VehicleLocation> getLatest(@PathVariable Long id) {
        Optional<VehicleLocation> latest = vehicleService.getLatestLocation(id);
        return latest.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/location/history")
    public List<VehicleLocation> getHistory(@PathVariable Long id, @RequestParam String from, @RequestParam String to) {
        return vehicleService.getHistory(id, Instant.parse(from), Instant.parse(to));
    }

}

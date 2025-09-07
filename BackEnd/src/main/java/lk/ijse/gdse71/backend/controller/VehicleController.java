package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.dto.VehicleLocationDTO;
import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;
import lk.ijse.gdse71.backend.service.VehicleService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

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
    public ResponseEntity<APIResponse> addLocation(@RequestBody VehicleLocationDTO dto) {
        Vehicle vehicle = vehicleService.getVehicleById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        VehicleLocation location = new VehicleLocation();
        location.setVehicle(vehicle);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setSpeed(dto.getSpeed());
        location.setTimestamp(Instant.parse(dto.getTimestamp()));

        vehicleService.saveLocation(location);

        return ResponseEntity.ok(new APIResponse(200, "Location saved", location));
    }

    @GetMapping("/{vehicleId}/latest")
    public ResponseEntity<?> getLatestLocation(@PathVariable Long vehicleId) {
        return vehicleService.getLatestLocation(vehicleId)
                .map(loc -> ResponseEntity.ok(new APIResponse(200,"Latest location", loc)))
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/{vehicleId}/recent")
    public ResponseEntity<APIResponse> getRecentLocations(@PathVariable Long vehicleId) {
        List<VehicleLocation> locations = vehicleService.getRecentLocations(vehicleId);
        return ResponseEntity.ok(new APIResponse(200 , "Recent locations", locations));
    }

}

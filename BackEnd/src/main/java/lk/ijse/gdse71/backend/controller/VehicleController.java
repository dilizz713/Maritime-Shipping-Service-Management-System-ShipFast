package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.VehicleDTO;
import lk.ijse.gdse71.backend.service.VehicleService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addVehicle(
            @RequestParam String name,
            @RequestParam String numberPlate,
            @RequestParam String type,
            @RequestParam String model,
            @RequestParam String status,
            @RequestParam(required = false) MultipartFile image) {

        VehicleDTO dto = new VehicleDTO();
        dto.setName(name);
        dto.setNumberPlate(numberPlate);
        dto.setType(type);
        dto.setModel(model);
        dto.setStatus(status);

        vehicleService.addVehicle(dto, image);
        return ResponseEntity.ok(new APIResponse(201, "Vehicle added successfully", true));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse> updateVehicle(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String numberPlate,
            @RequestParam String type,
            @RequestParam String model,
            @RequestParam String status,
            @RequestParam(required = false) MultipartFile image) {

        // Create DTO with updated fields
        VehicleDTO dto = new VehicleDTO();
        dto.setName(name);
        dto.setNumberPlate(numberPlate);
        dto.setType(type);
        dto.setModel(model);
        dto.setStatus(status);

        // Call service to update vehicle
        vehicleService.updateVehicle(id, dto, image);

        return ResponseEntity.ok(new APIResponse(200, "Vehicle updated successfully", true));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(new APIResponse(201 , "Vehicle deleted successfully" , true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(new APIResponse(200 , "Vehicle retrieved successfully", vehicleService.getVehicleById(id)));
    }

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(new APIResponse(200, "Vehicles retrieved successfully", vehicles));
    }
}

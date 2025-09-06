package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ServiceDTO;
import lk.ijse.gdse71.backend.service.ServicesService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/service")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ServiceController {
    private final ServicesService servicesService;

    @PostMapping("/saveService")
    public ResponseEntity<APIResponse> addService(@RequestBody ServiceDTO serviceDTO) {
        servicesService.save(serviceDTO);
        return ResponseEntity.ok(new APIResponse(201 , "Service saved successfully", true));
    }

    @GetMapping("/getAllServices")
    public ResponseEntity<APIResponse> getAllServices() {
        List<ServiceDTO> serviceDTOS = servicesService.getAllServices();
        return ResponseEntity.ok(new APIResponse(200,"All services retrieved successfully", serviceDTOS));
    }

    @PutMapping("/updateService")
    public ResponseEntity<APIResponse> updateService(@RequestBody ServiceDTO serviceDTO) {
        servicesService.update(serviceDTO);
        return ResponseEntity.ok(new APIResponse(200, "Service updated successfully", true));
    }

    @DeleteMapping("/deleteService/{id}")
    public ResponseEntity<APIResponse> deleteService(@PathVariable Long id) {
        servicesService.delete(id);
        return ResponseEntity.ok(new APIResponse(200, "Service deleted successfully", true));

    }
}

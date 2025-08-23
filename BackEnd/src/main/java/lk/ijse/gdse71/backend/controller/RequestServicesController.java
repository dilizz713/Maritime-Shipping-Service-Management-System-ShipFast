package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;
import lk.ijse.gdse71.backend.entity.Customer;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.CustomerRepository;
import lk.ijse.gdse71.backend.service.CustomerService;
import lk.ijse.gdse71.backend.service.RequestServicesService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/requestServices")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class RequestServicesController {
    private final RequestServicesService requestServicesService;
    private final CustomerRepository customerRepository;

    @PostMapping("/saveRequest")
    public ResponseEntity<APIResponse> saveRequest(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        requestServicesService.saveRequest(serviceRequestDTO);
        return ResponseEntity.ok(new APIResponse(201,"Request saved successfully",true));
    }

    @PutMapping("/updateRequest")
    public ResponseEntity<APIResponse> updateRequest(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        requestServicesService.updateRequest(serviceRequestDTO);
        return ResponseEntity.ok(new APIResponse(200,"Request updated successfully",true));
    }

    @GetMapping("/getAllRequestsByUser/{userId}")
    public ResponseEntity<APIResponse> getRequestsByUser(@PathVariable Long userId) {

        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for userId: " + userId));

        List<ServiceRequestDTO> requests = requestServicesService.getAllRequestsByCustomer(customer.getId());

        return ResponseEntity.ok(
                new APIResponse(200, "Requests retrieved successfully", requests)
        );
    }



}

package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.CustomerDTO;
import lk.ijse.gdse71.backend.service.CustomerService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/saveCustomer")
    public ResponseEntity<APIResponse> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.save(customerDTO);
        return ResponseEntity.ok(new APIResponse(201," Registered successfully" , true));
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<APIResponse> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.update(customerDTO);
        return ResponseEntity.ok(new APIResponse(200," Updated successfully" , true));
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<APIResponse> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok(new APIResponse(200," Deleted successfully" , true));
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<APIResponse> getAllCustomers() {
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();
        return ResponseEntity.ok(new APIResponse(200,"Customer list retrieved successfully", customerDTOS));
    }


}

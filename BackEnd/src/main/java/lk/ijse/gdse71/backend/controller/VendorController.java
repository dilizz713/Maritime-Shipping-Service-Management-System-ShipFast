package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.EmployeeDTO;
import lk.ijse.gdse71.backend.dto.VendorDTO;
import lk.ijse.gdse71.backend.service.VendorService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vendor")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/saveVendors")
    public ResponseEntity<APIResponse> saveVendor(@RequestBody VendorDTO vendorDTO) {
        vendorService.save(vendorDTO);
        return  ResponseEntity.ok(new APIResponse(201 , "Vendors saved successfully" , true));
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<APIResponse> updateVendor(@RequestBody VendorDTO vendorDTO) {
        vendorService.update(vendorDTO);
        return ResponseEntity.ok(new APIResponse(200 , "Vendor updated successfully" , true));

    }

    @DeleteMapping("/deleteVendor/{id}")
    public ResponseEntity<APIResponse> deleteVendor(@PathVariable Long id) {
        vendorService.delete(id);
        return ResponseEntity.ok(new APIResponse(200 , "Vendor deleted successfully" , true));

    }

    @GetMapping("/getAllVendors")
    public ResponseEntity<APIResponse> getAllEmployees() {
        List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
        return ResponseEntity.ok(new APIResponse(200 , "Vendors list retrieved successfully" , vendorDTOs));
    }
}

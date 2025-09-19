package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.ProvisionDTO;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/provisions")
@RequiredArgsConstructor
@CrossOrigin
public class ProvisionController {
    private final ProvisionService provisionService;

    @GetMapping("/products")
    public ResponseEntity<APIResponse> getAllProducts() {
        List<ProductDTO> products = provisionService.getAllProductsForCombo();
        return ResponseEntity.ok(new APIResponse(200, "Products fetched", products));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long id) {
        ProductDTO dto = provisionService.getProductDetail(id);
        return ResponseEntity.ok(new APIResponse(200, "Product fetched", dto));
    }

    @GetMapping("/jobs")
    public ResponseEntity<APIResponse> getJobsForProvisionAndShipStores() {
        List<JobDTO> jobs = provisionService.getJobsByServiceNames(List.of("Provision Supply", "Ship Stores Supply"));
        return ResponseEntity.ok(new APIResponse(200, "Jobs fetched", jobs));
    }

    @PostMapping("/create")
    public ResponseEntity<ProvisionDTO> saveProvision(@RequestBody ProvisionDTO provisionDTO) {
        ProvisionDTO savedProvision = provisionService.saveProvision(provisionDTO);
        return ResponseEntity.ok(savedProvision);
    }

    @GetMapping("/nextProvisionRef/{jobId}")
    public ResponseEntity<APIResponse> getNextProvisionRef(@PathVariable Long jobId) {
        String nextRef = provisionService.generateNextProvisionReference(jobId);
        return ResponseEntity.ok(new APIResponse(200, "Next Provision Reference", nextRef));
    }


}

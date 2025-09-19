package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.ProvisionDTO;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lk.ijse.gdse71.backend.util.ExcelGenerator;
import lk.ijse.gdse71.backend.util.ProvisionExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/byJob/{jobId}")
    public ResponseEntity<APIResponse> getProvisionsByJob(@PathVariable Long jobId) {
        List<ProvisionDTO> provisions = provisionService.getProvisionsByJobId(jobId);
        return ResponseEntity.ok(new APIResponse(200, "Provisions fetched", provisions));
    }

    @GetMapping("/details")
    public ResponseEntity<APIResponse> getProvisionDetails(@RequestParam Long jobId,
                                                           @RequestParam String provisionRef) {
        ProvisionDTO provision = provisionService.getProvisionDetails(jobId, provisionRef);
        return ResponseEntity.ok(new APIResponse(200, "Provision details fetched", provision));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportProvisionToExcel(
            @RequestParam Long jobId,
            @RequestParam String provisionRef) throws IOException {

        Provision provision = provisionService.getProvisionByJobAndRef(jobId, provisionRef);

        byte[] excelData = ProvisionExcelGenerator.generateProvisionExcel(provision);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + provisionRef + ".xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excelData);
    }

    @GetMapping("/downloadExcel")
    public ResponseEntity<byte[]> downloadProvisionExcel(@RequestParam Long jobId,
                                                         @RequestParam String provisionRef) throws IOException {
        Provision provision = provisionService.getProvisionByJobAndRef(jobId, provisionRef);
        byte[] excelData = ExcelGenerator.generateProvisionExcel(provision);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + provision.getProvisionReference() + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }




}

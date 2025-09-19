package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.CurrencyConversionRequestDTO;
import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.dto.ProductDTO;
import lk.ijse.gdse71.backend.dto.ProvisionDTO;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lk.ijse.gdse71.backend.util.ExcelGenerator;
import lk.ijse.gdse71.backend.util.ProvisionExcelGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/provisions")
@RequiredArgsConstructor
@CrossOrigin
public class ProvisionController {
    private final ProvisionService provisionService;

    @Value("${currency.api.key}")
    private String apiKey;


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
    public ResponseEntity<byte[]> downloadProvisionExcel(
            @RequestParam Long jobId,
            @RequestParam String provisionRef,
            @RequestParam(defaultValue = "LKR") String currency) throws IOException {

        Provision provision = provisionService.getProvisionByJobAndRef(jobId, provisionRef);

        if (!"LKR".equals(currency)) {
            double rate = getConversionRate("LKR", currency);
            provision.getItems().forEach(item -> item.setUnitPrice(item.getUnitPrice() * rate));
        }

        byte[] excelData = ExcelGenerator.generateProvisionExcel(provision);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + provision.getProvisionReference() + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }

    @PostMapping("/convert")
    public ResponseEntity<?> convertCurrency(@RequestBody CurrencyConversionRequestDTO dto) {
        try {
            String url = String.format(
                    "https://v6.exchangerate-api.com/v6/%s/latest/%s",
                    apiKey, dto.getFrom()
            );

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !"success".equals(response.get("result"))) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(Map.of("success", false, "error", "Currency API did not return a result"));
            }


            Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");
            if (!rates.containsKey(dto.getTo())) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(Map.of("success", false, "error", "Target currency not found"));
            }

            double rate = ((Number) rates.get(dto.getTo())).doubleValue();
            double convertedAmount = dto.getAmount() * rate;

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "from", dto.getFrom(),
                    "to", dto.getTo(),
                    "amount", dto.getAmount(),
                    "convertedAmount", convertedAmount,
                    "rate", rate
            ));

        } catch (Exception e) {
            e.printStackTrace(); // Log exception to see root cause
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    private double getConversionRate(String from, String to) {
        String url = String.format(
                "https://v6.exchangerate-api.com/v6/%s/latest/%s",
                apiKey, from
        );

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"success".equals(response.get("result"))) {
            throw new RuntimeException("Currency API did not return a result");
        }

        Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");
        if (!rates.containsKey(to)) {
            throw new RuntimeException("Target currency not found");
        }

        return ((Number) rates.get(to)).doubleValue();
    }






}

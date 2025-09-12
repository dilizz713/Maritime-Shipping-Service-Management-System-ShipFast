package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.QuotationDTO;
import lk.ijse.gdse71.backend.service.QuotationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/vendor-quotations")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class VendorsQuotationController {
    private final QuotationService quotationService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmQuotation(@RequestBody QuotationDTO dto) {
        try {
            quotationService.confirmQuotation(dto);
            return ResponseEntity.ok("Quotation confirmed successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.service.GRNService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/grn")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class GRNController {
    private final GRNService grnService;

    @PostMapping("/{confirmId}/create")
    public ResponseEntity<GRNDTO> createGRN(@PathVariable Long confirmId) {
        return ResponseEntity.ok(grnService.createGRNFromVerification(confirmId));
    }

    @GetMapping("/by-bill/{billNumber}")
    public ResponseEntity<GRNDTO> getByBillNumber(@PathVariable String billNumber) {
        return ResponseEntity.ok(grnService.getGRNByBillNumber(billNumber));
    }
}

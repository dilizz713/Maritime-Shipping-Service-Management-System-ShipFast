package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.service.GRNService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/grn")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class GRNController {
    private final GRNService grnService;

    @GetMapping("/by-bill/{billNumber}")
    public ResponseEntity<?> getGrnByBillNumber(@PathVariable String billNumber) {
        try {
            GRNDTO grn = grnService.getGrnByBillNumber(billNumber);
            return ResponseEntity.ok(grn);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGrn(@RequestBody GRNDTO dto) {
        try {
            grnService.updateGRN(dto);
            return ResponseEntity.ok(Map.of("message", "GRN updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}

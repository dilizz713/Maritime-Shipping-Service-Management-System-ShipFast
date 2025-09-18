package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.PendingPODTO;
import lk.ijse.gdse71.backend.service.PendingPOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pending-po")
@RequiredArgsConstructor
@CrossOrigin
public class PendingPOController {
    private final PendingPOService pendingPOService;

    @GetMapping("/getAll")
    public List<PendingPODTO> getAllPendingPOs() {
        return pendingPOService.getAllPendingPOs();
    }

    @PostMapping("/updateDescription/{pendingPOId}")
    public ResponseEntity<?> updateDescription(
            @PathVariable Long pendingPOId,
            @RequestParam String description) {
        try {
            pendingPOService.updateDescription(pendingPOId, description);
            return ResponseEntity.ok(Map.of("message", "Pending PO description updated successfully!"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(400).body(Map.of("error", ex.getMessage()));
        }
    }
}

package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.UOMDTO;
import lk.ijse.gdse71.backend.service.UOMService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/uom")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UOMController {
    private final UOMService uomService;

    @GetMapping("/getAll")
    public ResponseEntity<APIResponse> getAllUOMs() {
        log.info("Fetching all UOMs");
        List<UOMDTO> uoms = uomService.getAllUOMs();
        return ResponseEntity.ok(new APIResponse(200, "UOMs retrieved successfully", uoms));
    }

}

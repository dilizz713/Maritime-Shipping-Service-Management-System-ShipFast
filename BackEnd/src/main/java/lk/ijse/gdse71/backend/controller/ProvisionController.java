package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProvisionManageDTO;
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

    @GetMapping("/jobs")
    public ResponseEntity<APIResponse> getProvisionJobs() {
        List<ProvisionManageDTO> jobs = provisionService.getProvisionJobs();
        return ResponseEntity.ok(new APIResponse(200, "Provision jobs fetched successfully", jobs));
    }

    @GetMapping("/jobs/search")
    public ResponseEntity<APIResponse> searchProvisionJobs(@RequestParam String ref) {
        List<ProvisionManageDTO> jobs = provisionService.searchProvisionJobs(ref);

        if (jobs.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new APIResponse(404, "No provision jobs found for reference: " + ref, null));
        }

        return ResponseEntity.ok(
                new APIResponse(200, "Provision jobs search results", jobs)
        );
    }
}

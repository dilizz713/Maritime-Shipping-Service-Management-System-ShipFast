package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProvisionManageDTO;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

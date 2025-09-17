package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.service.JobService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
@CrossOrigin
public class JobController {
    private final JobService jobService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createJob(@RequestBody JobDTO jobDTO) {
        try {
            jobService.createJob(jobDTO);
            return ResponseEntity.ok(new APIResponse(201, "Job created successfully!", true));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new APIResponse(400, ex.getMessage(), false));
        }
    }
}

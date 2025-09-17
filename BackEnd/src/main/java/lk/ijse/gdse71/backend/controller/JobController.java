package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.service.JobService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAllJobs")
    public ResponseEntity<?> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok().body(jobs);
    }

    @PutMapping("/update/{jobId}")
    public ResponseEntity<APIResponse> updateJob(@PathVariable Long jobId, @RequestBody JobDTO jobDTO) {
        try {
            jobService.updateJob(jobId, jobDTO);
            return ResponseEntity.ok(new APIResponse(200, "Job updated successfully!", true));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new APIResponse(400, ex.getMessage(), false));
        }
    }

    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<APIResponse> deleteJob(@PathVariable Long jobId) {
        try {
            jobService.deleteJob(jobId);
            return ResponseEntity.ok(new APIResponse(200, "Job deleted successfully!", true));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new APIResponse(400, ex.getMessage(), false));
        }
    }

    @GetMapping("/getJob/{jobId}")
    public ResponseEntity<?> getJob(@PathVariable Long jobId) {
        try {
            JobDTO jobDTO = jobService.getJobById(jobId);
            return ResponseEntity.ok(jobDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404)
                    .body(new APIResponse(404, ex.getMessage(), false));
        }
    }
}

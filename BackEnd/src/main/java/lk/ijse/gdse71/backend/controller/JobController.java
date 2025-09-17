package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.service.JobService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
@CrossOrigin
public class JobController {
    private final JobService jobService;
    private final JobRepository jobRepository;

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createJob(@RequestBody JobDTO jobDTO) {
        /*try {
            jobService.createJob(jobDTO);
            return ResponseEntity.ok(new APIResponse(201, "Job created successfully!", true));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new APIResponse(400, ex.getMessage(), false));
        }*/
        try {
            JobDTO savedJob = jobService.createJob(jobDTO); // now returns saved job with actual filename
            return ResponseEntity.ok(new APIResponse(201, "Job created successfully!", savedJob));
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

    @PostMapping("/uploadRefFile/{jobId}")
    public ResponseEntity<?> uploadRefFile(@PathVariable Long jobId,
                                           @RequestParam("file") MultipartFile file) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        try {
            String uploadDir = "uploads";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename(); // keep UUID to avoid collision
            String filePath = uploadDir + "/" + fileName;
            Files.write(Paths.get(filePath), file.getBytes());

            job.setReferenceFilePath(fileName);
            jobRepository.save(job);

            return ResponseEntity.ok(Map.of("message", "File uploaded successfully", "fileName", fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error saving file"));
        }
    }
}

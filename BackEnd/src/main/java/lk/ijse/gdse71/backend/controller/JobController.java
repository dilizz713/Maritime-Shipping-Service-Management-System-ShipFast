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
    public ResponseEntity<APIResponse> createJob(@ModelAttribute JobDTO jobDTO) {

        try {
            JobDTO savedJob = jobService.createJob(jobDTO);
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


    @PostMapping("/update/{jobId}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long jobId,
            @RequestParam Long customerId,
            @RequestParam Long vesselId,
            @RequestParam Long portId,
            @RequestParam Long employeeId,
            @RequestParam Long serviceId,
            @RequestParam String remark,
            @RequestParam(required = false) MultipartFile referenceFile,
            @RequestParam(required = false) String referenceFilePath
    ) {
        try {
            JobDTO jobDTO = JobDTO.builder()
                    .customerId(customerId)
                    .vesselId(vesselId)
                    .portId(portId)
                    .employeeId(employeeId)
                    .serviceId(serviceId)
                    .remark(remark)
                    .referenceFile(referenceFile)
                    .referenceFilePath(referenceFilePath)
                    .build();

            jobService.updateJob(jobId, jobDTO);
            return ResponseEntity.ok(Map.of("message", "Job updated successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            java.io.File file = new java.io.File("uploads/" + fileName);
            if (!file.exists()) throw new RuntimeException("File not found");

            byte[] data = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .body(data);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
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

            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
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


    @PostMapping("/{jobId}/sendJobEmail")
    public ResponseEntity<Map<String, Object>> sendJobEmail(@PathVariable Long jobId) {
        Map<String, Object> result = jobService.sendJobEmail(jobId);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/{jobId}/sendToPendingPO")
    public ResponseEntity<Map<String, Object>> sendToPendingPO(
            @PathVariable Long jobId,
            @RequestParam(required = false) String description
    ) {
        try {
            Map<String, Object> result = jobService.sendJobToPendingPO(jobId, description);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }





}
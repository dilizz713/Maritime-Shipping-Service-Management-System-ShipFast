package lk.ijse.gdse71.backend.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lk.ijse.gdse71.backend.dto.JobScheduleDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.JobSchedule;
import lk.ijse.gdse71.backend.entity.PendingPO;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.repo.JobScheduleRepository;
import lk.ijse.gdse71.backend.repo.PendingPORepository;
import lk.ijse.gdse71.backend.service.JobSceduleService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/job-schedule")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class JobScheduleController {
    private final JobSceduleService jobScheduleService;

    private final JobRepository jobRepository;
    private final JobScheduleRepository jobScheduleRepository;
    private final PendingPORepository pendingPORepository;


    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody JobScheduleDTO dto) {
        if (dto.getJobId() == null) {
            log.error("JobId is null in DTO: {}", dto);
            return ResponseEntity.badRequest().body("JobId is required");
        }
        jobScheduleService.saveScheduleAndUpdateJob(dto);
        return ResponseEntity.ok("Job schedule created, Job updated, and PendingPO deleted successfully!");
    }

    @PostMapping("/approve/{jobId}")
    public ResponseEntity<APIResponse> approveJob(@PathVariable Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if(optionalJob.isEmpty()){
            return ResponseEntity.ok(new APIResponse(404 , "Job not found", false));
        }

        Job job = optionalJob.get();

        // 1️ Save JobSchedule
        JobSchedule schedule = JobSchedule.builder()
                .job(job)
                .status("Scheduled")
                .originalPO(0.0)
                .matchedPO(0.0)
                .remarks("Approved via quotation")
                .build();
        jobScheduleRepository.save(schedule);

       // Update Pending PO Status
        PendingPO pendingPO = pendingPORepository.findByJobId(jobId);
        if (pendingPO != null) {
            pendingPO.setStatus("Approved");
            pendingPORepository.save(pendingPO);
        }


        return ResponseEntity.ok(new APIResponse(200, "Job approved successfully" , true) );
    }
}

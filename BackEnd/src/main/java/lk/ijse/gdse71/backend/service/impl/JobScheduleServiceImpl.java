package lk.ijse.gdse71.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.gdse71.backend.dto.JobScheduleDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.JobSchedule;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.repo.JobScheduleRepository;
import lk.ijse.gdse71.backend.repo.PendingPORepository;
import lk.ijse.gdse71.backend.service.JobSceduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobScheduleServiceImpl implements JobSceduleService {
    private final JobRepository jobRepository;
    private final JobScheduleRepository jobScheduleRepository;
    private final PendingPORepository pendingPORepository;

    @Override
    @Transactional
    public void saveScheduleAndUpdateJob(JobScheduleDTO dto) {
        log.info("=== Transaction START: saveScheduleAndUpdateJob ===");
        log.info("Received DTO: {}", dto);

        try {
            // 1. Find job
            Job job = jobRepository.findById(dto.getJobId())
                    .orElseThrow(() -> {
                        log.error("Job not found with ID {}", dto.getJobId());
                        return new RuntimeException("Job not found with ID " + dto.getJobId());
                    });
            log.info("Job found: {} (Reference: {})", job.getId(), job.getJobReference());

            // 2. Save schedule
            JobSchedule schedule = JobSchedule.builder()
                    .status(dto.getStatus())
                    .originalPO(dto.getOriginalPO())
                    .matchedPO(dto.getMatchedPO())
                    .remarks(dto.getRemarks())
                    .job(job)
                    .build();

            jobScheduleRepository.save(schedule);
            log.info("JobSchedule created with ID: {}", schedule.getId());

            // 3. Remove job from PendingPO
            Long deleted = pendingPORepository.deleteByJobId(dto.getJobId());
            log.info("PendingPO delete executed. Rows affected = {}", deleted);

            // 4. Update job status → DONE
            job.setStatus("DONE");
            jobRepository.save(job);
            log.info("Job status updated to DONE for jobId {}", job.getId());

            log.info("=== Transaction COMMIT: saveScheduleAndUpdateJob ===");
        } catch (Exception e) {
            log.error("Transaction ROLLBACK triggered due to exception: {}", e.getMessage(), e);
            throw e;
        }
    }

}

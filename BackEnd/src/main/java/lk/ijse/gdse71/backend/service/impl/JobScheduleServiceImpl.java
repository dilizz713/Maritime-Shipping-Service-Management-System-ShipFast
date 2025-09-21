package lk.ijse.gdse71.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.gdse71.backend.dto.JobScheduleDTO;
import lk.ijse.gdse71.backend.dto.JobScheduleInfoDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.JobSchedule;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.repo.JobScheduleRepository;
import lk.ijse.gdse71.backend.repo.PendingPORepository;
import lk.ijse.gdse71.backend.service.JobSceduleService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

            Job job = jobRepository.findById(dto.getJobId())
                    .orElseThrow(() -> {
                        log.error("Job not found with ID {}", dto.getJobId());
                        return new RuntimeException("Job not found with ID " + dto.getJobId());
                    });
            log.info("Job found: {} (Reference: {})", job.getId(), job.getJobReference());


            JobSchedule schedule = JobSchedule.builder()
                    .status(dto.getStatus())
                    .originalPO(dto.getOriginalPO())
                    .matchedPO(dto.getMatchedPO())
                    .remarks(dto.getRemarks())
                    .job(job)
                    .build();

            jobScheduleRepository.save(schedule);
            log.info("JobSchedule created with ID: {}", schedule.getId());


            Long deleted = pendingPORepository.deleteByJobId(dto.getJobId());
            log.info("PendingPO delete executed. Rows affected = {}", deleted);


            job.setStatus("DONE");
            jobRepository.save(job);
            log.info("Job status updated to DONE for jobId {}", job.getId());

            log.info("=== Transaction COMMIT: saveScheduleAndUpdateJob ===");
        } catch (Exception e) {
            log.error("Transaction ROLLBACK triggered due to exception: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<JobScheduleInfoDTO> getAllJobSchedules() {
        List<JobSchedule> schedules = jobScheduleRepository.findAll();

        return schedules.stream().map(schedule -> JobScheduleInfoDTO.builder()
                        .id(schedule.getId())
                        .jobReference(schedule.getJob().getJobReference())
                        .jobDate(schedule.getJob().getDate())
                        .customerName(schedule.getJob().getCustomer().getCompanyName())
                        .vesselName(schedule.getJob().getVessel().getName())
                        .serviceName(schedule.getJob().getService().getServiceName())
                        .portName(schedule.getJob().getPort().getPortName())
                        .originalPO(schedule.getOriginalPO())
                        .matchedPO(schedule.getMatchedPO())
                        .status(schedule.getStatus())
                        .employeeName(schedule.getJob().getEmployee().getName())
                        .remark(schedule.getJob().getRemark())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public APIResponse updateJobSchedule(Long scheduleId, JobScheduleInfoDTO dto) {
        Optional<JobSchedule> optionalSchedule = jobScheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) {
            return new APIResponse(404, "Job Schedule not found", false);
        }

        JobSchedule schedule = optionalSchedule.get();

        if (dto.getOriginalPO() != null) schedule.setOriginalPO(dto.getOriginalPO());
        if (dto.getMatchedPO() != null) schedule.setMatchedPO(dto.getMatchedPO());
        if (dto.getRemark() != null) schedule.getJob().setRemark(dto.getRemark());
        if (dto.getStatus() != null) schedule.setStatus(dto.getStatus());

        jobScheduleRepository.save(schedule);

        return new APIResponse(200, "Job Schedule updated successfully", true);
    }

    @Override
    public JobScheduleInfoDTO getJobScheduleById(Long scheduleId) {
        Optional<JobSchedule> optionalSchedule = jobScheduleRepository.findById(scheduleId);
        if (optionalSchedule.isEmpty()) {
            return null;
        }

        JobSchedule schedule = optionalSchedule.get();

        return JobScheduleInfoDTO.builder()
                .id(schedule.getId())
                .jobReference(schedule.getJob().getJobReference())
                .jobDate(schedule.getJob().getDate())
                .customerName(schedule.getJob().getCustomer().getCompanyName())
                .vesselName(schedule.getJob().getVessel().getName())
                .serviceName(schedule.getJob().getService().getServiceName())
                .portName(schedule.getJob().getPort().getPortName())
                .originalPO(schedule.getOriginalPO())
                .matchedPO(schedule.getMatchedPO())
                .status(schedule.getStatus())
                .employeeName(schedule.getJob().getEmployee().getName())
                .remark(schedule.getJob().getRemark())
                .build();
    }

}

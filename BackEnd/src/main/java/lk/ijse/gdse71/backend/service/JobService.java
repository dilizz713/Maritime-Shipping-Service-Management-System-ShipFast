package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobDTO;

import java.util.List;

public interface JobService {
    JobDTO createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    void updateJob(Long jobId, JobDTO jobDTO);

    void deleteJob(Long jobId);

    JobDTO getJobById(Long jobId);
}

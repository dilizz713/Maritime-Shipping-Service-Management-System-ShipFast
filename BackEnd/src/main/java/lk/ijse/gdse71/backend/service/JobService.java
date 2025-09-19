package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.Job;

import java.util.List;
import java.util.Map;

public interface JobService {
    JobDTO createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    void updateJob(Long jobId, JobDTO jobDTO);

    void deleteJob(Long jobId);

    JobDTO getJobById(Long jobId);

    Map<String, Object> sendJobEmail(Long jobId);


    Map<String, Object> sendJobToPendingPO(Long jobId, String description);


}

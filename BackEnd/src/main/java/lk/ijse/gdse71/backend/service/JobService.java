package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobDTO;

import java.util.List;

public interface JobService {
    void createJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();
}

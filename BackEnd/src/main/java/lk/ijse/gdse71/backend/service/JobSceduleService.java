package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobScheduleDTO;
import lk.ijse.gdse71.backend.dto.JobScheduleInfoDTO;
import lk.ijse.gdse71.backend.util.APIResponse;

import java.util.List;

public interface JobSceduleService {
    void saveScheduleAndUpdateJob(JobScheduleDTO dto);

    List<JobScheduleInfoDTO> getAllJobSchedules();


    APIResponse updateJobSchedule(Long scheduleId, JobScheduleInfoDTO dto);

    JobScheduleInfoDTO getJobScheduleById(Long scheduleId);
}

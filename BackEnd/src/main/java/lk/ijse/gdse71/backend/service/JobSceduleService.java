package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.JobScheduleDTO;

public interface JobSceduleService {
    void saveScheduleAndUpdateJob(JobScheduleDTO dto);
}

package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ProvisionManageDTO;

import java.util.List;

public interface ProvisionService {
    List<ProvisionManageDTO> getProvisionJobs();
}

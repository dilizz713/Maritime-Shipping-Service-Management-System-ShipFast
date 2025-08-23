package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;

import java.util.List;

public interface RequestServicesService {
    void saveRequest(ServiceRequestDTO serviceRequestDTO);

    void updateRequest(ServiceRequestDTO serviceRequestDTO);

}

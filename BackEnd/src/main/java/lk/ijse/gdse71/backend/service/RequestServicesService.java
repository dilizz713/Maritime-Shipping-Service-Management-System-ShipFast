package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ServiceRequestDTO;

import java.util.List;

public interface RequestServicesService {
    void saveRequest(ServiceRequestDTO serviceRequestDTO);

    void updateRequest(ServiceRequestDTO serviceRequestDTO);

    List<ServiceRequestDTO> getAllRequestsByCustomer(Long customerId);

    List<ServiceRequestDTO> getAllRequests();

    void updateStatus(Long id, String status);
}

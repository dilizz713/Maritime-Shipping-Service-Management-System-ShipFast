package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.ServiceDTO;

import java.util.List;

public interface ServicesService {
    void save(ServiceDTO serviceDTO);

    List<ServiceDTO> getAllServices();
}

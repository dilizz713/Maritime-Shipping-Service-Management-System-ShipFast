package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.PortDTO;

import java.util.List;

public interface PortService {
    void save(PortDTO portDTO);

    List<PortDTO> getAllPorts();
}

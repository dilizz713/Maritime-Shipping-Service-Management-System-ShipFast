package lk.ijse.gdse71.backend.service;

import lk.ijse.gdse71.backend.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeDTO employeeDTO);

    void update(EmployeeDTO employeeDTO);

    void delete(Long id);

    List<EmployeeDTO> getAllEmployees();
}

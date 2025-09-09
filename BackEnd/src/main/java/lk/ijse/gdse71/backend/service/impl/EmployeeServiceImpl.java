package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.EmployeeDTO;
import lk.ijse.gdse71.backend.entity.Employee;
import lk.ijse.gdse71.backend.exception.ResourceAlreadyExists;
import lk.ijse.gdse71.backend.exception.ResourceNotFoundException;
import lk.ijse.gdse71.backend.repo.EmployeeRepository;
import lk.ijse.gdse71.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByNic(employeeDTO.getNic());
        if(employee == null) {
            employeeRepository.save(modelMapper.map(employeeDTO, Employee.class));
        }else{
            throw new ResourceAlreadyExists("This employee has already exists!");
        }
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        Optional<Employee> employee = employeeRepository.findById(employeeDTO.getId());
        if(employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setName(employeeDTO.getName());
            existingEmployee.setAddress(employeeDTO.getAddress());
            existingEmployee.setNic(employeeDTO.getNic());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setPhone(employeeDTO.getPhone());
            existingEmployee.setDepartment(employeeDTO.getDepartment());

            employeeRepository.save(existingEmployee);
        }else{
            throw new ResourceNotFoundException("This Employee does not exist");
        }

    }

    @Override
    public void delete(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            employeeRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("This Employee does not exist");
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
       List<Employee> employeeList = employeeRepository.findAll();
         if(employeeList.isEmpty()) {
              throw new ResourceNotFoundException("No employees found");
         }
        return modelMapper.map(employeeList, new TypeToken<List<EmployeeDTO>>(){}.getType());
    }
}

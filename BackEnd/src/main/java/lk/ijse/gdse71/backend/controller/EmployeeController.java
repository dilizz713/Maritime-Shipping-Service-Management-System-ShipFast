package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.EmployeeDTO;
import lk.ijse.gdse71.backend.entity.Employee;
import lk.ijse.gdse71.backend.service.EmployeeService;
import lk.ijse.gdse71.backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/saveEmployee")
    public ResponseEntity<APIResponse> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.save(employeeDTO);
        return  ResponseEntity.ok(new APIResponse(201 , "Employee saved successfully" , true));
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<APIResponse> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.update(employeeDTO);
        return ResponseEntity.ok(new APIResponse(200 , "Employee updated successfully" , true));

    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<APIResponse> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(new APIResponse(200 , "Employee deleted successfully" , true));

    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<APIResponse> getAllEmployees() {
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();
        return ResponseEntity.ok(new APIResponse(200 , "Employee list retrieved successfully" , employeeDTOS));
    }
}

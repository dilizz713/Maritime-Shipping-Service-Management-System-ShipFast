package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.*;
import lk.ijse.gdse71.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final VesselsRepository vesselsRepository;
    private final PortRepository portRepository;
    private final ServiceRepository servicesRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void createJob(JobDTO jobDTO) {
// Validate customer
        Optional<Customer> customerOpt = customerRepository.findById(jobDTO.getCustomerId());
        if (customerOpt.isEmpty()) throw new RuntimeException("Customer not found");

        // Validate vessel belongs to customer
        Optional<Vessels> vesselOpt = vesselsRepository.findById(jobDTO.getVesselId());
        if (vesselOpt.isEmpty()) throw new RuntimeException("Vessel not found");
        if (!vesselOpt.get().getCustomer().getId().equals(jobDTO.getCustomerId())) {
            throw new RuntimeException("Vessel does not belong to selected customer");
        }

        // Validate port
        Optional<Port> portOpt = portRepository.findById(jobDTO.getPortId());
        if (portOpt.isEmpty()) throw new RuntimeException("Port not found");

        // Validate employee
        Optional<Employee> employeeOpt = employeeRepository.findById(jobDTO.getEmployeeId());
        if (employeeOpt.isEmpty()) throw new RuntimeException("Employee not found");

        // Validate services
        List<Services> serviceList = new ArrayList<>();
        if (jobDTO.getServiceIds() != null) {
            for (Long serviceId : jobDTO.getServiceIds()) {
                servicesRepository.findById(serviceId).ifPresent(serviceList::add);
            }
        }

        // Map DTO to Entity
        Job job = Job.builder()
                .date(jobDTO.getDate())
                .jobReference(jobDTO.getJobReference())
                .remark(jobDTO.getRemark())
                .status("PENDING") // default status
                .customer(customerOpt.get())
                .vessel(vesselOpt.get())
                .port(portOpt.get())
                .employee(employeeOpt.get())
                .services(serviceList)
                .referenceFilePath(jobDTO.getReferenceFilePath())
                .build();

        jobRepository.save(job);
    }
}

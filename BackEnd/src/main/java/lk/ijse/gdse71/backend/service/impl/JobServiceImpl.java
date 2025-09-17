package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.*;
import lk.ijse.gdse71.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CustomerRepository customerRepository;
    private final VesselsRepository vesselsRepository;
    private final PortRepository portRepository;
    private final ServiceRepository servicesRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createJob(JobDTO jobDTO) {
        Customer customer = customerRepository.findById(jobDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Vessels vessel = vesselsRepository.findById(jobDTO.getVesselId())
                .orElseThrow(() -> new RuntimeException("Vessel not found"));
        if (!vessel.getCustomer().getId().equals(customer.getId()))
            throw new RuntimeException("Vessel does not belong to selected customer");
        Port port = portRepository.findById(jobDTO.getPortId())
                .orElseThrow(() -> new RuntimeException("Port not found"));
        Employee employee = employeeRepository.findById(jobDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Services service = servicesRepository.findById(jobDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Job job = new Job();
        job.setCustomer(customer);
        job.setVessel(vessel);
        job.setPort(port);
        job.setEmployee(employee);
        job.setService(service);
        job.setRemark(jobDTO.getRemark());
        job.setReferenceFilePath(jobDTO.getReferenceFilePath());
        job.setStatus("PENDING");


        LocalDate today = LocalDate.now();
        job.setDate(java.sql.Date.valueOf(today));

        String datePart = today.format(DateTimeFormatter.ofPattern("ddMMyy"));
        long countToday = jobRepository.countByDate(java.sql.Date.valueOf(today));
        String jobRef = "ref" + datePart + String.format("%02d", countToday + 1);
        job.setJobReference(jobRef);


        jobRepository.save(job);


    }
}

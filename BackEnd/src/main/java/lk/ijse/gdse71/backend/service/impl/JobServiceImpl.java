package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.*;
import lk.ijse.gdse71.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


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
    public JobDTO createJob(JobDTO jobDTO) {
        /*Customer customer = customerRepository.findById(jobDTO.getCustomerId())
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


        jobRepository.save(job);*/

        LocalDate today = LocalDate.now();

        Job job = Job.builder()
                .customer(customerRepository.findById(jobDTO.getCustomerId()).orElseThrow())
                .vessel(vesselsRepository.findById(jobDTO.getVesselId()).orElseThrow())
                .port(portRepository.findById(jobDTO.getPortId()).orElseThrow())
                .employee(employeeRepository.findById(jobDTO.getEmployeeId()).orElseThrow())
                .service(servicesRepository.findById(jobDTO.getServiceId()).orElseThrow())
                .remark(jobDTO.getRemark())
                .status("New")
                .date(java.sql.Date.valueOf(today))
                .referenceFilePath(jobDTO.getReferenceFilePath())
                .build();

        // Format date as ddMMyy
        String datePart = today.format(DateTimeFormatter.ofPattern("ddMMyy"));

        // Count jobs created today
        long countToday = jobRepository.countByDate(java.sql.Date.valueOf(today));

        // Sequential number with 2 digits
        String seqNumber = String.format("%02d", countToday + 1);

        String jobRef = "Ref" + datePart + seqNumber;
        job.setJobReference(jobRef);

        jobRepository.save(job);
        return mapToDTO(job);

    }

    public JobDTO mapToDTO(Job job) {
        if (job == null) return null;

        return JobDTO.builder()
                .id(job.getId())
                .jobReference(job.getJobReference())
                .remark(job.getRemark())
                .status(job.getStatus())
                .customerId(job.getCustomer() != null ? job.getCustomer().getId() : null)
                .customerName(job.getCustomer() != null ? job.getCustomer().getCompanyName() : null)
                .vesselId(job.getVessel() != null ? job.getVessel().getId() : null)
                .vesselName(job.getVessel() != null ? job.getVessel().getName() : null)
                .portId(job.getPort() != null ? job.getPort().getId() : null)
                .portName(job.getPort() != null ? job.getPort().getPortName() : null)
                .employeeId(job.getEmployee() != null ? job.getEmployee().getId() : null)
                .employeeName(job.getEmployee() != null ? job.getEmployee().getName() : null)
                .serviceId(job.getService() != null ? job.getService().getId() : null)
                .serviceName(job.getService() != null ? job.getService().getServiceName() : null)
                .referenceFilePath(job.getReferenceFilePath())
                .dateAsString(job.getDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(job.getDate()) : null)
                .build();
    }


    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(job -> {
            JobDTO dto = new JobDTO();
            dto.setId(job.getId());
            dto.setJobReference(job.getJobReference());
            dto.setRemark(job.getRemark());
            dto.setStatus(job.getStatus());
            dto.setCustomerName(job.getCustomer().getCompanyName());
            dto.setVesselName(job.getVessel().getName());
            dto.setPortName(job.getPort().getPortName());
            dto.setServiceName(job.getService().getServiceName());
            dto.setEmployeeName(job.getEmployee().getName());
            dto.setReferenceFilePath(job.getReferenceFilePath());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dto.setDateAsString(sdf.format(job.getDate()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateJob(Long jobId, JobDTO jobDTO) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

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

        job.setCustomer(customer);
        job.setVessel(vessel);
        job.setPort(port);
        job.setEmployee(employee);
        job.setService(service);
        job.setRemark(jobDTO.getRemark());
        job.setReferenceFilePath(jobDTO.getReferenceFilePath());


        jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        jobRepository.delete(job);

    }

    @Override
    public JobDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setJobReference(job.getJobReference());
        dto.setRemark(job.getRemark());
        dto.setStatus(job.getStatus());
        dto.setCustomerId(job.getCustomer().getId());
        dto.setCustomerName(job.getCustomer().getCompanyName());
        dto.setVesselId(job.getVessel().getId());
        dto.setVesselName(job.getVessel().getName());
        dto.setPortId(job.getPort().getId());
        dto.setPortName(job.getPort().getPortName());
        dto.setEmployeeId(job.getEmployee().getId());
        dto.setEmployeeName(job.getEmployee().getName());
        dto.setServiceId(job.getService().getId());
        dto.setServiceName(job.getService().getServiceName());
        dto.setReferenceFilePath(job.getReferenceFilePath());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dto.setDateAsString(sdf.format(job.getDate()));

        return dto;
    }
}

package lk.ijse.gdse71.backend.service.impl;

import jakarta.mail.internet.MimeMessage;
import lk.ijse.gdse71.backend.dto.JobDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.*;
import lk.ijse.gdse71.backend.service.EmailService;
import lk.ijse.gdse71.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private final PendingPORepository pendingPORepository;

    private final JavaMailSender mailSender;
    private final EmailService emailService;

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        LocalDate today = LocalDate.now();

        Job job = Job.builder()
                .customer(customerRepository.findById(jobDTO.getCustomerId()).orElseThrow())
                .vessel(vesselsRepository.findById(jobDTO.getVesselId()).orElseThrow())
                .port(portRepository.findById(jobDTO.getPortId()).orElseThrow())
                .employee(employeeRepository.findById(jobDTO.getEmployeeId()).orElseThrow())
                .service(servicesRepository.findById(jobDTO.getServiceId()).orElseThrow())
                .remark(jobDTO.getRemark())
                .status("Pending")
                .date(java.sql.Date.valueOf(today))
                .referenceFilePath(jobDTO.getReferenceFilePath())
                .build();

        if (jobDTO.getReferenceFile() != null && !jobDTO.getReferenceFile().isEmpty()) {
            try {
                String uploadDir = "uploads";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "-" + jobDTO.getReferenceFile().getOriginalFilename();
                String filePath = uploadDir + "/" + fileName;
                Files.write(Paths.get(filePath), jobDTO.getReferenceFile().getBytes());

                job.setReferenceFilePath(fileName);
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + e.getMessage());
            }
        }

        String datePart = today.format(DateTimeFormatter.ofPattern("ddMMyy"));
        long countToday = jobRepository.countByDate(java.sql.Date.valueOf(today));
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

        // Handle file upload
        if (jobDTO.getReferenceFile() != null && !jobDTO.getReferenceFile().isEmpty()) {
            try {
                String uploadDir = "uploads";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "-" + jobDTO.getReferenceFile().getOriginalFilename();
                String filePath = uploadDir + "/" + fileName;
                Files.write(Paths.get(filePath), jobDTO.getReferenceFile().getBytes());

                job.setReferenceFilePath(fileName); // save the UUID-generated name
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + e.getMessage());
            }
        }


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

    @Override
    public Map<String, Object> sendJobEmail(Long jobId) {
        Map<String, Object> response = new HashMap<>();

        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isEmpty()) {
            response.put("success", false);
            response.put("message", "Job not found with ID: " + jobId);
            return response;
        }

        Job job = optionalJob.get();
        Employee employee = job.getEmployee();

        if (employee == null) {
            response.put("success", false);
            response.put("message", "No employee assigned to this job.");
            return response;
        }

        String employeeEmail = employee.getEmail();
        if (employeeEmail == null || employeeEmail.isBlank()) {
            response.put("success", false);
            response.put("message", "Employee email not found for job ID: " + jobId);
            return response;
        }

        String subject = "New Job Assignment - " + job.getJobReference();
        String body = String.format("""
            Hello %s,

            You have been assigned a new job. Please prepare the quotation for this job.

            📌 Customer: %s
            📌 Vessel: %s
            📌 Service: %s
            📌 Port: %s

            Regards,
            Operations Manager
            """,
                employee.getName(),
                job.getCustomer().getCompanyName(),
                job.getVessel().getName(),
                job.getService().getServiceName(),
                job.getPort().getPortName()
        );

        try {
            byte[] attachmentBytes = null;
            String attachmentName = null;

            if (job.getReferenceFilePath() != null && !job.getReferenceFilePath().isBlank()) {
                java.io.File file = new java.io.File("uploads/" + job.getReferenceFilePath());
                if (file.exists()) {
                    attachmentBytes = Files.readAllBytes(file.toPath());
                    attachmentName = file.getName();
                } else {
                    System.err.println("Reference file not found: " + file.getAbsolutePath());
                }
            }

            emailService.sendEmailWithOptionalAttachment(
                    employeeEmail,
                    subject,
                    body,
                    attachmentBytes,
                    attachmentName
            );

            response.put("success", true);
            response.put("message", "Email sent successfully to " + employeeEmail);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Map<String, Object> sendJobToPendingPO(Long jobId, String description) {
        Map<String, Object> response = new HashMap<>();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        PendingPO pendingPO = PendingPO.builder()
                .job(job)
                .description(description)
                .date(new Date())
                .build();

        pendingPORepository.save(pendingPO);

        job.setStatus("Processing");
        jobRepository.save(job);

        response.put("success", true);
        response.put("message", "Job sent to Pending PO successfully");
        response.put("pendingPOId", pendingPO.getId());

        return response;
    }


}

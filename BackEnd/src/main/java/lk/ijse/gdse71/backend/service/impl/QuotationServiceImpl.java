package lk.ijse.gdse71.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.gdse71.backend.dto.QuotationDTO;
import lk.ijse.gdse71.backend.dto.QuotationInfoDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.Quotation;
import lk.ijse.gdse71.backend.repo.JobRepository;
import lk.ijse.gdse71.backend.repo.QuotationRepository;
import lk.ijse.gdse71.backend.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {
    private final QuotationRepository quotationRepository;
    private final JobRepository jobRepository;

    private final String UPLOAD_DIR = "uploads/quotation/";


    @Override
    public List<QuotationInfoDTO> getQuotationsInfoByJob(Long jobId) {
        System.out.println("Fetching quotations for Job ID: " + jobId);

        List<Quotation> quotations = quotationRepository.findByJobId(jobId);
        System.out.println("Found " + quotations.size() + " quotations.");

        return quotations.stream()
                .map(q -> QuotationInfoDTO.builder()
                        .jobId(q.getJob().getId())
                        .jobReference(q.getJob().getJobReference())
                        .quotationNumber(q.getQuotationNumber())
                        .employeeName(q.getJob().getEmployee() != null ? q.getJob().getEmployee().getName() : "N/A")
                        .customerName(q.getJob().getCustomer() != null ? q.getJob().getCustomer().getCompanyName() : "N/A")
                        .vesselName(q.getJob().getVessel() != null ? q.getJob().getVessel().getName() : "N/A")
                        .quotationFile(q.getQuotationFile())
                        .quotationDate(q.getQuotationDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public QuotationDTO getQuotationById(Long id) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quotation not found with ID " + id));

        return QuotationDTO.builder()
                .id(quotation.getId())
                .jobId(quotation.getJob().getId())
                .jobReference(quotation.getJob().getJobReference())
                .quotationFile(quotation.getQuotationFile())
                .quotationDate(quotation.getQuotationDate())
                .quotationNumber(quotation.getQuotationNumber())
                .build();
    }

    @Override
    @Transactional
    public QuotationDTO saveQuotationFile(Long jobId, MultipartFile file) throws IOException {
        System.out.println("Job ID received: " + jobId);


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> {
                    System.out.println("ERROR: Job not found with ID " + jobId);
                    return new RuntimeException("Job not found");
                });
        System.out.println("Job found: " + job.getJobReference());


        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            System.out.println("Upload directory does not exist. Creating: " + UPLOAD_DIR);
            boolean created = uploadDir.mkdirs();
            System.out.println("Directory created: " + created);
        } else {
            System.out.println("Upload directory exists: " + UPLOAD_DIR);
        }


        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        System.out.println("Saving file to: " + filePath);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved successfully.");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to save file.");
            e.printStackTrace();
            throw e;
        }

        Date quotationDate = new Date();
        String vesselName = (job.getVessel() != null && job.getVessel().getName() != null)
                ? job.getVessel().getName().replaceAll("\\s+", "")
                : "UnknownVessel";
        String datePart = new SimpleDateFormat("ddMMyy").format(quotationDate);
        long count = quotationRepository.count() + 1;
        String quotationNumber = "Q" + vesselName + datePart + String.format("%02d", count);
        System.out.println("Generated quotation number: " + quotationNumber);
        System.out.println("Quotation date: " + quotationDate);



        Quotation quotation = Quotation.builder()
                .job(job)
                .quotationFile(fileName)
                .quotationDate(quotationDate)
                .quotationNumber(quotationNumber)
                .build();




        try {
            Quotation saved = quotationRepository.save(quotation);
            System.out.println("Quotation saved successfully with ID: " + saved.getId());
        } catch (Exception e) {
            System.out.println("ERROR: Failed to save quotation to DB.");
            e.printStackTrace();
            throw e;
        }

        job.setStatus("Done");
        jobRepository.save(job);


        return QuotationDTO.builder()
                .id(quotation.getId())
                .jobId(job.getId())
                .jobReference(job.getJobReference())
                .quotationFile(fileName)
                .quotationDate(quotation.getQuotationDate())
                .quotationNumber(quotation.getQuotationNumber())
                .build();


    }




}

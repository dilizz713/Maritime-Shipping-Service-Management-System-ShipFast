package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.QuotationDTO;
import lk.ijse.gdse71.backend.dto.QuotationInfoDTO;
import lk.ijse.gdse71.backend.entity.Quotation;
import lk.ijse.gdse71.backend.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/job-quotation")
@RequiredArgsConstructor
@CrossOrigin
public class JobQuotationController {
    private final QuotationService quotationService;


    @PostMapping("/upload/{jobId}")
    public ResponseEntity<Map<String, Object>> uploadQuotation(
            @PathVariable Long jobId,
            @RequestParam("file") MultipartFile file
    ) throws  IOException {
        QuotationDTO dto = quotationService.saveQuotationFile(jobId, file);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Quotation uploaded successfully");
        response.put("quotation", dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info-by-job/{jobId}")
    public ResponseEntity<List<QuotationInfoDTO>> getQuotationsInfoByJob(@PathVariable Long jobId) {
        List<QuotationInfoDTO> quotations = quotationService.getQuotationsInfoByJob(jobId);
        return ResponseEntity.ok(quotations);
    }
}

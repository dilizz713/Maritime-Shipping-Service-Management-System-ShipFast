package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.InquiryDTO;
import lk.ijse.gdse71.backend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inquiries")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping("/create")
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO dto) {
        return ResponseEntity.ok(inquiryService.saveInquiry(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<InquiryDTO> updateInquiry(@RequestBody InquiryDTO dto) {
        return ResponseEntity.ok(inquiryService.updateInquiry(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<InquiryDTO>> getAllInquiries() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquiryDTO> getInquiryById(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiryById(id));
    }
}

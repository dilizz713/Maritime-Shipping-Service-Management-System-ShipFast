package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ConfirmInquiryDTO;
import lk.ijse.gdse71.backend.dto.InquiryDTO;
import lk.ijse.gdse71.backend.dto.InquiryItemDTO;
import lk.ijse.gdse71.backend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/inquiries")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class InquiryController {
    private final InquiryService inquiryService;


    @PostMapping("/create")
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO dto) throws IOException {
        return ResponseEntity.ok(inquiryService.saveInquiry(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<InquiryDTO> updateInquiry(@RequestBody InquiryDTO dto) throws IOException {
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


    @PutMapping("/{inquiryId}/upload-excel")
    public ResponseEntity<?> uploadExcel(@PathVariable Long inquiryId, @RequestParam("file") MultipartFile file) {
        try {
            inquiryService.updateInquiryFromExcel(inquiryId, file.getBytes());
            InquiryDTO updatedInquiry = inquiryService.getInquiryById(inquiryId);
            return ResponseEntity.ok(updatedInquiry);
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to process Excel file. Please upload a correct file."));
        }
    }

    @GetMapping("/{inquiryId}/excel")
    public ResponseEntity<byte[]> getInquiryExcel(@PathVariable Long inquiryId) throws IOException {
        Path path = Paths.get("uploads/inquiries/Inquiry_" + inquiryId + ".xlsx");
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        byte[] fileContent = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=Inquiry_" + inquiryId + ".xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }

    @PutMapping("/{inquiryId}/items/{itemId}")
    public ResponseEntity<InquiryItemDTO> updateItem(
            @PathVariable Long inquiryId,
            @PathVariable Long itemId,
            @RequestBody InquiryItemDTO dto) {

        return ResponseEntity.ok(inquiryService.updateInquiryItem(inquiryId, itemId, dto));
    }

    @DeleteMapping("/{inquiryId}/items/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long inquiryId,
            @PathVariable Long itemId) {

        inquiryService.deleteInquiryItem(inquiryId, itemId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmInquiry(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String description = body.get("description");
            ConfirmInquiryDTO confirmedInquiry = inquiryService.confirmInquiry(id, description);
            return ResponseEntity.ok(confirmedInquiry);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }



}

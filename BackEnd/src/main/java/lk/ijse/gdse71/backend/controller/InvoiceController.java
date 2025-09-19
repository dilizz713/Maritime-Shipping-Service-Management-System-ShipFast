package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProvisionInvoiceDTO;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.service.impl.ProvisionInvoiceService;
import lk.ijse.gdse71.backend.util.ProvisionInvoiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoice")
@RequiredArgsConstructor
@CrossOrigin
public class InvoiceController {
    private final ProvisionService provisionService;
    private final ProvisionInvoiceService provisionInvoiceService;

    /*@GetMapping("/provision-invoice")
    public ResponseEntity<byte[]> generateProvisionInvoice(@RequestParam Long provisionId) throws Exception {
        Provision provision = provisionService.getProvisionById(provisionId);
        ProvisionInvoiceDTO invoiceDTO = ProvisionInvoiceMapper.toInvoiceDTO(provision);

        byte[] pdfData = provisionInvoiceService.generateInvoice(invoiceDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + provision.getProvisionReference() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }*/

    /*@GetMapping("/provision-invoice")
    public ResponseEntity<byte[]> generateProvisionInvoice(@RequestParam Long provisionId) throws Exception {

        // Get provision
        Provision provision = provisionService.getProvisionById(provisionId);

        // Map to invoice DTO
        ProvisionInvoiceDTO invoiceDTO = ProvisionInvoiceMapper.toInvoiceDTO(provision);

        // Generate PDF
        byte[] pdfData = provisionInvoiceService.generateInvoice(invoiceDTO);

        // Return as PDF download
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + provision.getProvisionReference() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }*/

    @GetMapping("/provision-invoice")
    public ResponseEntity<byte[]> generateProvisionInvoice(@RequestParam String provisionRef) throws Exception {
        Provision provision = provisionService.getProvisionByReference(provisionRef); // lookup by reference
        ProvisionInvoiceDTO invoiceDTO = ProvisionInvoiceMapper.toInvoiceDTO(provision);
        byte[] pdfData = provisionInvoiceService.generateInvoice(invoiceDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + provision.getProvisionReference() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
}

package lk.ijse.gdse71.backend.controller;

import lk.ijse.gdse71.backend.dto.ProvisionInvoiceDTO;
import lk.ijse.gdse71.backend.dto.ServiceInvoiceDTO;
import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.service.JobService;
import lk.ijse.gdse71.backend.service.ProvisionService;
import lk.ijse.gdse71.backend.service.impl.CurrencyServiceImpl;
import lk.ijse.gdse71.backend.service.impl.ProvisionInvoiceService;
import lk.ijse.gdse71.backend.util.ProvisionInvoiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("api/v1/invoice")
@RequiredArgsConstructor
@CrossOrigin
public class InvoiceController {
    private final ProvisionService provisionService;
    private final ProvisionInvoiceService provisionInvoiceService;
    private final CurrencyServiceImpl currencyService;
    private final JobService jobService;


    @GetMapping("/provision-invoice")
    public ResponseEntity<byte[]> generateProvisionInvoice(
            @RequestParam String provisionRef,
            @RequestParam(defaultValue = "LKR") String currency
    ) throws Exception {

        Provision provision = provisionService.getProvisionByReference(provisionRef);
        ProvisionInvoiceDTO invoiceDTO = ProvisionInvoiceMapper.toInvoiceDTO(provision);


        if (!"LKR".equalsIgnoreCase(currency)) {
            invoiceDTO.getItems().forEach(item -> {
                double converted = currencyService.convert(item.getUnitCost(), "LKR", currency);
                item.setUnitCost(converted);
            });
            invoiceDTO.setCurrency(currency);


            double total = invoiceDTO.getItems().stream()
                    .mapToDouble(i -> i.getUnitCost() * i.getQuantity())
                    .sum();
            invoiceDTO.setTotalAmount(total);
        }

        byte[] pdfData = provisionInvoiceService.generateInvoice(invoiceDTO);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + provision.getProvisionReference() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }


}

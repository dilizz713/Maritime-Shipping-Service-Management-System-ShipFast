package lk.ijse.gdse71.backend.service.impl;

import jakarta.mail.MessagingException;
import lk.ijse.gdse71.backend.dto.InquiryDTO;
import lk.ijse.gdse71.backend.dto.InquiryItemDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.InquiryRepository;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lk.ijse.gdse71.backend.service.EmailService;
import lk.ijse.gdse71.backend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lk.ijse.gdse71.backend.util.ExcelGenerator;
import org.apache.poi.ss.usermodel.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepo;
    private final VendorRepository vendorRepo;
    private final ProductRepository productRepo;

    private final EmailService emailService;

    @Override
    public InquiryDTO saveInquiry(InquiryDTO dto) throws IOException {
        Vendor vendor = vendorRepo.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Inquiry inquiry = Inquiry.builder()
                .vendor(vendor)
                .description(dto.getDescription())
                .inquiryDate(dto.getInquiryDate())
                .inquiryStatus(dto.getInquiryStatus() != null ? dto.getInquiryStatus() : InquiryStatus.PENDING)
                .build();

        List<InquiryItem> items = dto.getItems().stream().map(itemDTO -> {
            Product product = productRepo.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            return InquiryItem.builder()
                    .inquiry(inquiry)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(itemDTO.getUnitPrice())
                    .discount(itemDTO.getDiscount())
                    .totalAmount(itemDTO.getTotalAmount())
                    .status(itemDTO.getStatus())
                    .build();
        }).collect(Collectors.toList());

        inquiry.setItems(items);

        Inquiry saved = inquiryRepo.save(inquiry);
        dto.setId(saved.getId());

        // Generate Excel
        byte[] excelData = ExcelGenerator.generateInquiryExcel(saved);

        // Send Email
        try {
            emailService.sendEmailWithAttachment(
                    vendor.getEmail(),
                    "New Inquiry #" + saved.getId(),
                    "Dear " + vendor.getName() + ",\nPlease find attached the inquiry.",
                    excelData,
                    "Inquiry_" + saved.getId() + ".xlsx"
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return dto;
    }

    @Override
    public InquiryDTO updateInquiry(InquiryDTO dto) throws IOException {
        return saveInquiry(dto);
    }

    @Override
    public List<InquiryDTO> getAllInquiries() {
        /*return inquiryRepo.findAll().stream().map(inquiry -> InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .totalAmount(item.getTotalAmount())
                        .status(item.getStatus())
                        .build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());*/

        return inquiryRepo.findAll().stream().map(inquiry -> InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .vendorName(inquiry.getVendor().getName()) // add this
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName()) // add this
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .totalAmount(item.getTotalAmount())
                        .status(item.getStatus())
                        .build()).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    @Override
    public InquiryDTO getInquiryById(Long id) {
        /*Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        return InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .totalAmount(item.getTotalAmount())
                        .status(item.getStatus())
                        .build()).collect(Collectors.toList()))
                .build();*/
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        return InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .vendorName(inquiry.getVendor().getName()) // add this
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName()) // add this
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .totalAmount(item.getTotalAmount())
                        .status(item.getStatus())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Override
    public void updateInquiryFromExcel(Long inquiryId, byte[] excelData) throws Exception {
        Inquiry inquiry = inquiryRepo.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        InputStream inputStream = new ByteArrayInputStream(excelData);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header row
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String productName = row.getCell(0).getStringCellValue();
            double quantity = row.getCell(1).getNumericCellValue();
            double unitPrice = row.getCell(2).getNumericCellValue();
            double discount = row.getCell(3).getNumericCellValue();
            double totalAmount = row.getCell(4).getNumericCellValue();
            String statusStr = row.getCell(5).getStringCellValue();

            // Find the InquiryItem by product name
            InquiryItem item = inquiry.getItems().stream()
                    .filter(it -> it.getProduct().getName().equals(productName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product " + productName + " not found in inquiry"));

            // Update values
            item.setQuantity((int) quantity);
            item.setUnitPrice(unitPrice);
            item.setDiscount(discount);
            item.setTotalAmount(totalAmount);
            item.setStatus(statusStr.equalsIgnoreCase("AVAILABLE") ? ProductStatus.AVAILABLE : ProductStatus.NOT_AVAILABLE);
        }

        workbook.close();
        inquiryRepo.save(inquiry);
    }
}

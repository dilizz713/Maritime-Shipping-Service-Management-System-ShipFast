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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lk.ijse.gdse71.backend.util.ExcelGenerator;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.nio.file.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepo;
    private final VendorRepository vendorRepo;
    private final ProductRepository productRepo;

    private static final String UPLOAD_DIR = "uploads/inquiries/";

    private final EmailService emailService;
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

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
            String emailBody = "Dear " + vendor.getName() + ",\n\n" +
                    saved.getDescription() + "\n\n" +
                    "Best regards,\n" +
                    "Ship Fast Team";

            emailService.sendEmailWithAttachment(
                    vendor.getEmail(),
                    "Requesting New Inquiry",
                    emailBody,
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
        return inquiryRepo.findAll().stream().map(inquiry -> InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .vendorName(inquiry.getVendor().getName())
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .excelFileName(inquiry.getExcelFileName())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
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
        Inquiry inquiry = inquiryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        return InquiryDTO.builder()
                .id(inquiry.getId())
                .vendorId(inquiry.getVendor().getId())
                .vendorName(inquiry.getVendor().getName())
                .description(inquiry.getDescription())
                .inquiryDate(inquiry.getInquiryDate())
                .inquiryStatus(inquiry.getInquiryStatus())
                .excelFileName(inquiry.getExcelFileName())
                .items(inquiry.getItems().stream().map(item -> InquiryItemDTO.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .totalAmount(item.getTotalAmount())
                        .status(item.getStatus())
                        .remarks(item.getRemarks())
                        .build()).collect(Collectors.toList()))
                .build();
    }


    @Override
    public void updateInquiryFromExcel(Long inquiryId, byte[] excelData) throws Exception {
        Inquiry inquiry = inquiryRepo.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));


        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        String filename = "Inquiry_" + inquiryId + ".xlsx";
        Path excelPath = uploadPath.resolve(filename);
        Files.write(excelPath, excelData);


        inquiry.setExcelFileName(filename);

        try (InputStream inputStream = new ByteArrayInputStream(excelData);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String productName = getCellString(row.getCell(0));
                double quantity = getCellNumeric(row.getCell(1));
                double unitPrice = getCellNumeric(row.getCell(2));
                double discount = getCellNumeric(row.getCell(3));
                double totalAmount = getCellNumeric(row.getCell(4));
                String statusStr = getCellString(row.getCell(5));
                String remarks = getCellString(row.getCell(6));

                if (productName.isEmpty()) continue;


                InquiryItem item = inquiry.getItems().stream()
                        .filter(it -> it.getProduct().getName().equalsIgnoreCase(productName))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(
                                "Product '" + productName + "' not found in inquiry #" + inquiryId));


                item.setQuantity((int) quantity);
                item.setUnitPrice(unitPrice);
                item.setDiscount(discount);
                item.setTotalAmount(totalAmount);
                if (statusStr.equalsIgnoreCase("AVAILABLE")) {
                    item.setStatus(ProductStatus.AVAILABLE);
                } else {
                    item.setStatus(ProductStatus.NOT_AVAILABLE);
                }
                item.setRemarks(remarks);
            }
            inquiryRepo.save(inquiry);
        }
    }


    private String getCellString(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private double getCellNumeric(Cell cell) {
        if (cell == null) return 0;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public String saveExcelForInquiry(Long inquiryId, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = "Inquiry_" + inquiryId + ".xlsx";
        Path excelPath = uploadPath.resolve(filename);
        Files.write(excelPath, file.getBytes());


        Inquiry inquiry = inquiryRepo.findById(inquiryId)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setExcelFileName(filename);
        inquiryRepo.save(inquiry);

        return filename;
    }

    public List<Map<String, Object>> parseExcelToJson(MultipartFile file) throws IOException {
        List<Map<String, Object>> rows = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, Object> rowData = new HashMap<>();
                rowData.put("productName", getCellString(row.getCell(0)));
                rowData.put("quantity", getCellNumeric(row.getCell(1)));
                rowData.put("unitPrice", getCellNumeric(row.getCell(2)));
                rowData.put("discount", getCellNumeric(row.getCell(3)));
                rowData.put("totalAmount", getCellNumeric(row.getCell(4)));
                rowData.put("status", getCellString(row.getCell(5)));
                rowData.put("remarks", getCellString(row.getCell(6)));

                rows.add(rowData);
            }
        }
        return rows;
    }


}

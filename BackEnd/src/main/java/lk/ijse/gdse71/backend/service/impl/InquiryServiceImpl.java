package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.InquiryDTO;
import lk.ijse.gdse71.backend.dto.InquiryItemDTO;
import lk.ijse.gdse71.backend.entity.*;
import lk.ijse.gdse71.backend.repo.InquiryRepository;
import lk.ijse.gdse71.backend.repo.ProductRepository;
import lk.ijse.gdse71.backend.repo.VendorRepository;
import lk.ijse.gdse71.backend.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepo;
    private final VendorRepository vendorRepo;
    private final ProductRepository productRepo;

    @Override
    public InquiryDTO saveInquiry(InquiryDTO dto) {
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
        return dto;
    }

    @Override
    public InquiryDTO updateInquiry(InquiryDTO dto) {
        return saveInquiry(dto);
    }

    @Override
    public List<InquiryDTO> getAllInquiries() {
        return inquiryRepo.findAll().stream().map(inquiry -> InquiryDTO.builder()
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
                .build()).collect(Collectors.toList());
    }

    @Override
    public InquiryDTO getInquiryById(Long id) {
        Inquiry inquiry = inquiryRepo.findById(id)
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
                .build();
    }
}

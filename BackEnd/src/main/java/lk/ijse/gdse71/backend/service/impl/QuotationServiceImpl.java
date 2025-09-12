package lk.ijse.gdse71.backend.service.impl;


import lk.ijse.gdse71.backend.dto.QuotationDTO;
import lk.ijse.gdse71.backend.entity.Inquiry;
import lk.ijse.gdse71.backend.entity.InquiryItem;
import lk.ijse.gdse71.backend.entity.Quotation;
import lk.ijse.gdse71.backend.entity.QuotationItem;
import lk.ijse.gdse71.backend.repo.InquiryRepository;
import lk.ijse.gdse71.backend.repo.QuotationRepository;
import lk.ijse.gdse71.backend.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {
    private final QuotationRepository quotationRepo;
    private final InquiryRepository inquiryRepo;

    @Override
    public void confirmQuotation(QuotationDTO dto) {
        Inquiry inquiry = inquiryRepo.findById(dto.getInquiryId())
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        Quotation quotation = Quotation.builder()
                .inquiry(inquiry)
                .billNumber(dto.getBillNumber())
                .totalAmount(dto.getTotalAmount())
                .build();

        List<QuotationItem> items = dto.getItems().stream().map(itemDTO -> {
            InquiryItem inquiryItem = inquiry.getItems().stream()
                    .filter(i -> i.getId().equals(itemDTO.getItemId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Inquiry item not found: " + itemDTO.getItemId()));

            return QuotationItem.builder()
                    .quotation(quotation)
                    .inquiryItem(inquiryItem)
                    .discount(itemDTO.getDiscount())
                    .amount(itemDTO.getAmount())
                    .build();
        }).collect(Collectors.toList());

        quotation.setItems(items);

        quotationRepo.save(quotation);
    }
}

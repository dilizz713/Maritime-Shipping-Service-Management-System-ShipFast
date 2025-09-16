package lk.ijse.gdse71.backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.dto.GRNItemDTO;
import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
import lk.ijse.gdse71.backend.entity.GRN;
import lk.ijse.gdse71.backend.entity.GRNItem;
import lk.ijse.gdse71.backend.repo.ConfirmInquiryRepository;
import lk.ijse.gdse71.backend.repo.GRNRepository;
import lk.ijse.gdse71.backend.service.GRNService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GRNServiceImpl implements GRNService {

    private final ConfirmInquiryRepository confirmInquiryRepo;
    private final GRNRepository grnRepo;

    @Override
    public GRNDTO getGRNByBillNumber(String billNumber) {
        GRN grn = grnRepo.findByConfirmInquiry_BillNumber(billNumber)
                .orElseThrow(() -> new RuntimeException("GRN not found for bill number: " + billNumber));
        return mapToDTO(grn);
    }

    @Override
    @Transactional
    public GRNDTO createGRNFromVerification(Long confirmId) {
        ConfirmInquiry confirm = confirmInquiryRepo.findById(confirmId)
                .orElseThrow(() -> new RuntimeException("Confirm Inquiry not found"));

        List<GRNItem> items = confirm.getInquiry().getItems().stream()
                .map(i -> GRNItem.builder()
                        .productCode(i.getProduct().getCode())
                        .productName(i.getProduct().getName())
                        .productType(i.getProduct().getProductType().name())
                        .uom(i.getProduct().getUom().getUomCode())
                        .qty(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .discount(0.0)
                        .amount(i.getQuantity() * i.getUnitPrice())
                        .description("Auto-loaded from verification")
                        .build())
                .collect(Collectors.toList());

        double total = items.stream().mapToDouble(GRNItem::getAmount).sum();

        GRN grn = GRN.builder()
                .grnNumber("GRN-" + System.currentTimeMillis())
                .receivedDate(LocalDate.now())
                .totalAmount(total)
                .remark("Auto-generated after verification")
                .confirmInquiry(confirm)
                .items(items)
                .build();

        items.forEach(it -> it.setGrn(grn));

        GRN saved = grnRepo.save(grn);

        return mapToDTO(saved);
    }

    private GRNDTO mapToDTO(GRN grn) {
        return GRNDTO.builder()
                .id(grn.getId())
                .grnNumber(grn.getGrnNumber())
                .receivedDate(grn.getReceivedDate())
                .totalAmount(grn.getTotalAmount())
                .remark(grn.getRemark())
                .billNumber(grn.getConfirmInquiry().getBillNumber())
                .vendorName(grn.getConfirmInquiry().getInquiry().getVendor().getName())
                .items(grn.getItems().stream().map(it -> GRNItemDTO.builder()
                        .id(it.getId())
                        .productCode(it.getProductCode())
                        .productName(it.getProductName())
                        .productType(it.getProductType())
                        .uom(it.getUom())
                        .qty(it.getQty())
                        .unitPrice(it.getUnitPrice())
                        .discount(it.getDiscount())
                        .amount(it.getAmount())
                        .description(it.getDescription())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}

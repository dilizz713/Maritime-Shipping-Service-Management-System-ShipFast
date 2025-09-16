package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.dto.GRNItemDTO;
import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
import lk.ijse.gdse71.backend.entity.GRN;
import lk.ijse.gdse71.backend.repo.GRNRepository;
import lk.ijse.gdse71.backend.service.GRNService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GRNServiceImpl implements GRNService {
    private final GRNRepository grnRepo;

    @Override
    public GRNDTO getGrnByBillNumber(String billNumber) {
        GRN grn = grnRepo.findByBillNumber(billNumber)
                .orElseThrow(() -> new RuntimeException("GRN not found for bill number: " + billNumber));

        ConfirmInquiry confirm = grn.getConfirmInquiry();

        return GRNDTO.builder()
                .id(grn.getId())
                .grnNumber(grn.getGrnNumber())
                .receivedDate(grn.getReceivedDate())
                .totalAmount(grn.getTotalAmount())
                .remark(grn.getRemark())
                .billNumber(confirm.getBillNumber())
                .vendorName(confirm.getInquiry().getVendor().getName())
                .items(grn.getItems().stream().map(i -> GRNItemDTO.builder()
                        .id(i.getId())
                        .productCode(i.getProductCode())
                        .productName(i.getProductName())
                        .productType(i.getProductType())
                        .uom(i.getUom())
                        .qty(i.getQty())
                        .unitPrice(i.getUnitPrice())
                        .discount(i.getDiscount())
                        .amount(i.getAmount())
                        .description(i.getDescription())
                        .build()).toList())
                .build();
    }
}

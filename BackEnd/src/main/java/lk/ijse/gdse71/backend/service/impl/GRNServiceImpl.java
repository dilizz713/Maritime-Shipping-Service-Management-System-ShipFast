package lk.ijse.gdse71.backend.service.impl;

import lk.ijse.gdse71.backend.dto.GRNDTO;
import lk.ijse.gdse71.backend.dto.GRNItemDTO;
import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
import lk.ijse.gdse71.backend.entity.GRN;
import lk.ijse.gdse71.backend.entity.GRNItem;
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
                        .discountPercentage(0.0)
                        .discount(i.getDiscount())
                        .amount(i.getAmount())
                        .description(i.getDescription())
                        .build()).toList())
                .build();
    }

    @Override
    public void updateGRN(GRNDTO dto) {
        GRN grn = grnRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("GRN not found"));

        grn.setRemark(dto.getRemark());

        grn.getItems().forEach(item -> {
            dto.getItems().stream()
                    .filter(i -> i.getId().equals(item.getId()))
                    .findFirst()
                    .ifPresent(updated -> {
                        item.setQty(updated.getQty());
                        item.setUnitPrice(updated.getUnitPrice());
                        item.setDescription(updated.getDescription());


                        double discount = (updated.getDiscountPercentage() != null) ?
                                (item.getQty() * item.getUnitPrice() * updated.getDiscountPercentage() / 100.0)
                                : 0.0;

                        item.setDiscount(discount);


                        item.setAmount((item.getQty() * item.getUnitPrice()) - discount);
                    });
        });


        double totalAmount = grn.getItems().stream()
                .mapToDouble(GRNItem::getAmount)
                .sum();
        grn.setTotalAmount(totalAmount);

        grnRepo.save(grn);
    }

}









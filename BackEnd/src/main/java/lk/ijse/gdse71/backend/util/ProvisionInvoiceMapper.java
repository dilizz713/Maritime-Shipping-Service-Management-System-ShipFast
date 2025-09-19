package lk.ijse.gdse71.backend.util;

import lk.ijse.gdse71.backend.dto.ProvisionInvoiceDTO;
import lk.ijse.gdse71.backend.dto.ProvisionInvoiceItemDTO;
import lk.ijse.gdse71.backend.entity.Provision;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;


public class ProvisionInvoiceMapper {
    public static ProvisionInvoiceDTO toInvoiceDTO(Provision provision) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<ProvisionInvoiceItemDTO> items = provision.getItems().stream()
                .map(item -> ProvisionInvoiceItemDTO.builder()
                        .name(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitCost(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        double totalAmount = items.stream()
                .mapToDouble(i -> i.getUnitCost() * i.getQuantity())
                .sum();

        return ProvisionInvoiceDTO.builder()
                .from("Ship Fast Pvt Ltd")
                .to(provision.getJob().getCustomer().getCompanyName())
                .number(provision.getProvisionReference())
                .date(sdf.format(provision.getProvisionDate()))
                .dueDate(null)
                .notes("Thank you for your business")
                .logoUrl("../FrontEnd/assets/ship%logo.png")
                .currency("LKR")
                .items(items)
                .totalAmount(totalAmount)
                .build();
    }
}

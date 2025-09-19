package lk.ijse.gdse71.backend.util;

import lk.ijse.gdse71.backend.dto.ServiceInvoiceDTO;
import lk.ijse.gdse71.backend.entity.Job;

import java.text.SimpleDateFormat;

public class ServiceInvoiceMapper {
    public static ServiceInvoiceDTO toInvoiceDTO(Job job, String currency, double convertedAmount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return ServiceInvoiceDTO.builder()
                .from("Ship Fast Pvt Ltd")
                .to(job.getCustomer().getCompanyName())
                .jobReference(job.getJobReference())
                .date(sdf.format(job.getDate()))
                .dueDate(null)
                .notes("Thank you for your business")
                .logoUrl("https://your-company-logo-url.com/logo.png")
                .currency(currency)
                .totalAmount(convertedAmount)
                .build();
    }
}

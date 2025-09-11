package lk.ijse.gdse71.backend.dto;

import lk.ijse.gdse71.backend.entity.InquiryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InquiryDTO {
    private Long id;
    private Long vendorId;
    private LocalDate inquiryDate;
    private String description;
    private InquiryStatus inquiryStatus;
    private List<InquiryItemDTO> items;
}

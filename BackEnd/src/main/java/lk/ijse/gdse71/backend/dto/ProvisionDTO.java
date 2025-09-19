package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionDTO {
    private Long id;
    private String provisionReference;
    private String description;
    private String status;
    private Date provisionDate;
    private Long jobId;
    private List<ProvisionItemDTO> items;
}
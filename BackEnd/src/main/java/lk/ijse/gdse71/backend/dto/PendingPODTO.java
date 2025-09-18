package lk.ijse.gdse71.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingPODTO {
    private Long id;
    private String description;
    private Date date;

    private JobDTO job;
}

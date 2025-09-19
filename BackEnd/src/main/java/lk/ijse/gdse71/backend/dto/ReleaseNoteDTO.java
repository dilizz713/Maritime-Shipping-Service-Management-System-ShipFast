package lk.ijse.gdse71.backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReleaseNoteDTO {
    private Long provisionItemId;
    private Long productId;
    private Integer stockQty;
    private String remark;
}

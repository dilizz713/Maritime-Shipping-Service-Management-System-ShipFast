package lk.ijse.gdse71.backend.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "confirm_inquiry_id", nullable = false)
    private ConfirmInquiry confirmInquiry;

    @ManyToOne
    @JoinColumn(name = "inquiry_item_id", nullable = false)
    private InquiryItem inquiryItem;

    private Integer receivedQty;
    private Boolean correct;
    private String description;
}

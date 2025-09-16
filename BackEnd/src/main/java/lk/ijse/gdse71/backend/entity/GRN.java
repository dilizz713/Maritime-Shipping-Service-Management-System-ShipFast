package lk.ijse.gdse71.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GRN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grnNumber;

    private LocalDate receivedDate;

    private Double totalAmount;

    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirm_inquiry_id")
    private ConfirmInquiry confirmInquiry;

    @OneToMany(mappedBy = "grn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GRNItem> items;


}

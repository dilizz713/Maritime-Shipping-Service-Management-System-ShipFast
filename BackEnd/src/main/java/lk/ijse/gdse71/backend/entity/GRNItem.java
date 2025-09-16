package lk.ijse.gdse71.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GRNItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;
    private String productName;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String uom;
    private Integer qty;
    private Double unitPrice;
    private Double discount;
    private Double amount;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_id")
    private GRN grn;
}

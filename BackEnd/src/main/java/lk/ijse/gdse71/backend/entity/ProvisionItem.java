package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "provision_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProvisionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;
    private String productName;
    private String uomCode;
    private Double unitPrice;

    private Integer quantity;
    private String remark;

    @ManyToOne
    @JoinColumn(name = "provision_id")
    private Provision provision;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
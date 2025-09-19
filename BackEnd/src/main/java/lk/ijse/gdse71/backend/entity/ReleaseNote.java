package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReleaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Integer stockQty;
    private String remark;

    @ManyToOne
    @JoinColumn(name = "provision_item_id", nullable = false)
    private ProvisionItem item;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

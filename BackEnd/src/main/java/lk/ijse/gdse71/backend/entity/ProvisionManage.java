package lk.ijse.gdse71.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "provision_manage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvisionManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String remark;

    private Double qty;

    private Double totalAmount;

    private Double discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToMany
    @JoinTable(
            name = "provision_products",
            joinColumns = @JoinColumn(name = "provision_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}

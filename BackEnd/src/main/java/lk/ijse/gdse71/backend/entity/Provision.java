package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Provision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provision_reference")
    private String provisionReference;
    private String description;
    private String status;
    private Date provisionDate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @OneToMany(mappedBy = "provision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProvisionItem> items;


}

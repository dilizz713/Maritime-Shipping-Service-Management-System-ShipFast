package lk.ijse.gdse71.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String jobReference;
    private String remark;
    private String status;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "vessel_id", nullable = false, unique = true)
    private Vessels vessel;

    @OneToOne
    @JoinColumn(name = "port_id", nullable = false, unique = true)
    private Port port;

    @ManyToMany
    @JoinTable(
            name = "job_services",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Services> services;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String referenceFilePath;
}

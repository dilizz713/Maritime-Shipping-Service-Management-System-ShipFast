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

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "vessel_id", nullable = false)
    private Vessels vessel;


    @ManyToOne
    @JoinColumn(name = "port_id", nullable = false)
    private Port port;


    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;


    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String referenceFilePath;

    @OneToOne(mappedBy = "job")
    private PendingPO pendingPO;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobSchedule> schedules;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Provision> provisions;


}

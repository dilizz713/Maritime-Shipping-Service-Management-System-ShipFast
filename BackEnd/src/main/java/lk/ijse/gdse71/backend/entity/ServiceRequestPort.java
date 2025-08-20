package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceRequestPort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialInstructions;
    private Date scheduleDate;

    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port port;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ServiceRequest serviceRequest;
}

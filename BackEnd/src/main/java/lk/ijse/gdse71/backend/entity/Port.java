package lk.ijse.gdse71.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String portName;
    private String location;

    @OneToMany(mappedBy = "port", cascade = CascadeType.ALL)
    private List<ServiceRequest> serviceRequest;

    @OneToMany(mappedBy = "port" , cascade = CascadeType.ALL)
    private List<Job> job;
}

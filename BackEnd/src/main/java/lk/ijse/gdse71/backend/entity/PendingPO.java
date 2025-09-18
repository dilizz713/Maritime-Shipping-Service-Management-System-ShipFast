package lk.ijse.gdse71.backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PendingPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Date date;
    private String status;

    @OneToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
}

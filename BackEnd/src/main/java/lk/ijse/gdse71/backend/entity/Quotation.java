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
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quotationNumber;
    private Date quotationDate;
    private String quotationFile;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;


}

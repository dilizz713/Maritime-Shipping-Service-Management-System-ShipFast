package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    List<Quotation> findByJobId(Long jobId);
}

package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.GRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GRNRepository extends JpaRepository<GRN, Long> {
    Optional<GRN> findByConfirmInquiry_BillNumber(String billNumber);
}

package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.GRN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GRNRepository extends JpaRepository<GRN, Long> {

    @Query("SELECT g FROM GRN g WHERE g.confirmInquiry.billNumber = :billNumber")
    Optional<GRN> findByBillNumber(@Param("billNumber") String billNumber);
}

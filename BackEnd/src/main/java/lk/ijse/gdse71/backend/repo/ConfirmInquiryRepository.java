package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ConfirmInquiryRepository extends JpaRepository<ConfirmInquiry, Long> {
    long countByConfirmationDate(LocalDate now);

    Optional<ConfirmInquiry> findByInquiryId(Long inquiryId);
}

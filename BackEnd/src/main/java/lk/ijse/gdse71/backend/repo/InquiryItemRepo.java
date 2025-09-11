package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.InquiryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryItemRepo extends JpaRepository<InquiryItem,Long> {
}

package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
import lk.ijse.gdse71.backend.entity.ReceivedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivedProductRepository extends JpaRepository<ReceivedProduct, Long> {

}

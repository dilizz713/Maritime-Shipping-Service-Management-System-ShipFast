package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ProvisionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisionItemRepository extends JpaRepository<ProvisionItem,Long> {
}

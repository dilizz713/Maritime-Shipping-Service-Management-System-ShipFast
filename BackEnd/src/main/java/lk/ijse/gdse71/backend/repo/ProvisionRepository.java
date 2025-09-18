package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ProvisionManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisionRepository extends JpaRepository<ProvisionManage,Long> {
}

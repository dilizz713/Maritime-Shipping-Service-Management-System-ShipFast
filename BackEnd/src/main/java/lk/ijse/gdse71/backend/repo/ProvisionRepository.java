package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.Provision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisionRepository extends JpaRepository<Provision,Long> {

    long countByJobId(Long jobId);
}

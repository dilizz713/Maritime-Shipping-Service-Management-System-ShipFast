package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.Provision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProvisionRepository extends JpaRepository<Provision,Long> {

    long countByJobId(Long jobId);

    List<Provision> findByJobId(Long jobId);

    Optional<Provision> findByJobIdAndProvisionReference(Long jobId, String provisionRef);

    Optional<Provision> findByProvisionReference(String provisionReference);
}

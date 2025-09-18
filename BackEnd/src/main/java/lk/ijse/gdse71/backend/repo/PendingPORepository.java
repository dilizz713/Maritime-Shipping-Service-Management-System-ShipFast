package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Job;
import lk.ijse.gdse71.backend.entity.PendingPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingPORepository extends JpaRepository<PendingPO, Long> {
    Long deleteByJobId(Long jobId);


    PendingPO findByJobId(Long jobId);
}

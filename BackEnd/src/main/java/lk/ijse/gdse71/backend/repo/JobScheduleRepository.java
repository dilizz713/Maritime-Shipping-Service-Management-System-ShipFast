package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.JobSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobScheduleRepository extends JpaRepository<JobSchedule, Long> {
}

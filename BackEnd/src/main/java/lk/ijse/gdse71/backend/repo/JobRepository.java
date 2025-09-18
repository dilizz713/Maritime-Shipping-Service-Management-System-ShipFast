package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    long countByDate(java.sql.Date date);

    List<Job> findByStatus(String pending);

    @Query("SELECT j FROM Job j WHERE j.service.serviceName IN ('Provision Supply', 'Supply of Ship Stores')")
    List<Job> findProvisionJobs();
}

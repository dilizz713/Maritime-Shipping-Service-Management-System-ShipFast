package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestServiceRepository extends JpaRepository<ServiceRequest , Long> {
    List<ServiceRequest> findByCustomerId(Long customerId);
}

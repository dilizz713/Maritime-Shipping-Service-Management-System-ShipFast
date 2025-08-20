package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

    Services findByServiceName(String serviceName);
}

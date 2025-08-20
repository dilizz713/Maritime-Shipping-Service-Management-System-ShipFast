package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortRepository extends JpaRepository<Port, Long> {
    Port findByPortName(String portName);
}

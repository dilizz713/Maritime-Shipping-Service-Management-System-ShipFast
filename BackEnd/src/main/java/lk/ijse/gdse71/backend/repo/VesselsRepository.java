package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Vessels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselsRepository extends JpaRepository<Vessels, Long> {
}

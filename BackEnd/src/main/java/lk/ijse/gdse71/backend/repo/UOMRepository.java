package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.UOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UOMRepository extends JpaRepository<UOM,Long> {
    Optional<UOM> findByUomNameIgnoreCase(String uomName);
}

package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.GRNItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GRNItemRepository extends JpaRepository<GRNItem, Long> {
}

package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Vendor findByEmail(String email);
}

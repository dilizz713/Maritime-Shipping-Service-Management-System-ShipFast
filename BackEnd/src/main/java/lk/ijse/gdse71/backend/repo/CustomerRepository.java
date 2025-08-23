package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmailAndCompanyName(String email, String companyName);

    Optional<Customer> findByUserId(Long userId);
}

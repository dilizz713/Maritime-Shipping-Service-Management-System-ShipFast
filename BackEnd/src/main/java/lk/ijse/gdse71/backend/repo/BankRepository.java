package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    boolean existsByName(String name);

    boolean existsBySwiftCode(String swiftCode);

    Optional<Bank> findByName(String bankName);
}

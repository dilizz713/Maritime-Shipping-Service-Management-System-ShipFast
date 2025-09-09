package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    BankAccount findByAccountNumber(String accountNumber);

    List<BankAccount> findByVendorId(Long vendorId);
}

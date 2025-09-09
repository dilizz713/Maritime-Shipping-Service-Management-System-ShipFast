package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByNic(String nic);
}

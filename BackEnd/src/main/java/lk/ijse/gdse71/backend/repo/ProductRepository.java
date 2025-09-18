package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findTopByOrderByIdDesc();

    List<Product> findByNameContainingIgnoreCase(String q);
}

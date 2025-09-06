package lk.ijse.gdse71.backend.repo;

import lk.ijse.gdse71.backend.entity.Vehicle;
import lk.ijse.gdse71.backend.entity.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocation,Long> {

    @Query("SELECT vl FROM VehicleLocation vl WHERE vl.vehicle.id = :vehicleId ORDER BY vl.timestamp DESC LIMIT 1")
    Optional<VehicleLocation> findLatestByVehicleId(Long vehicleId);

    List<VehicleLocation> findByVehicleAndTimestampBetween(Vehicle vehicle, Instant from, Instant to);
}

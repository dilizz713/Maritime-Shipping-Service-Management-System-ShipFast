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

    @Query("SELECT v FROM VehicleLocation v WHERE v.vehicle.id = :vehicleId ORDER BY v.timestamp DESC")
    List<VehicleLocation> findTop10ByVehicleIdOrderByTimestampDesc(Long vehicleId);

    @Query("SELECT v FROM VehicleLocation v WHERE v.vehicle.id = :vehicleId ORDER BY v.timestamp DESC LIMIT 1")
    Optional<VehicleLocation> findLatestByVehicleId(Long vehicleId);

}

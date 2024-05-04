package pe.edu.bikeswap.bike_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.bikeswap.bike_service.entity.BikeEntity;

@Repository
public interface BikeRepository extends JpaRepository<BikeEntity, Long> {
}
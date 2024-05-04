package pe.edu.bikeswap.bike_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.bikeswap.bike_service.entity.BikeEntity;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Availability {
    private Long id;

    private boolean availabilityType;

    private LocalDate availabilityStartDate;

    private LocalDate availabilityEndDate;

    private BikeEntity bicycle;
}

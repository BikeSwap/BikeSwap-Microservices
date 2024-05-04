package pe.edu.bikeswap.bike_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bike {
    private Long id;

    private String bicycleName;

    private String bicycleDescription;

    private double bicyclePrice;

    private String bicycleSize;

    private String bicycleModel;

    private String imageData;

    private Long userId;
}

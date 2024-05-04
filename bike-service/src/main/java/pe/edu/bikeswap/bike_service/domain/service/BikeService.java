package pe.edu.bikeswap.bike_service.domain.service;

import pe.edu.bikeswap.bike_service.domain.model.Bike;

import java.time.LocalDate;
import java.util.List;

public interface BikeService {
    public abstract Bike createBike(Bike bike);
    public abstract Bike getBikeById(Long bicycle_id);
    public abstract Bike updateBike(Long bicycleId, Bike bike);
    public abstract void deleteBike(Long bicycle_id);
    public abstract List<Bike> getAllBikes();
    public abstract List<Bike> getAllAvailableBikes(LocalDate start_date, LocalDate end_date);
}

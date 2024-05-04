package pe.edu.bikeswap.bike_service.domain.service;

import pe.edu.bikeswap.bike_service.application.dto.AvailabilityDto;
import pe.edu.bikeswap.bike_service.domain.model.Availability;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    public abstract Availability create(AvailabilityDto availabilityDto);
    public abstract Availability getById(Long availability_id);
    public abstract void delete(Long availability_id);

    public abstract boolean existsBetweenDates(Long bicycle_id, LocalDate availability_start_date, LocalDate availability_end_date);
    public abstract List<Availability> getByBikeId(Long bicycle_id);
    public abstract List<Availability> getByBicycleIdAndAvailabilityType(Long bicycle_id, boolean availability_type);
}

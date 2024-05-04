package pe.edu.bikeswap.bike_service.application.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pe.edu.bikeswap.bike_service.application.dto.AvailabilityDto;
import pe.edu.bikeswap.bike_service.application.exception.ResourceNotFoundException;
import pe.edu.bikeswap.bike_service.application.exception.ValidationException;
import pe.edu.bikeswap.bike_service.application.mapper.AvailabilityMapper;
import pe.edu.bikeswap.bike_service.domain.model.Availability;
import pe.edu.bikeswap.bike_service.domain.model.Bike;
import pe.edu.bikeswap.bike_service.domain.repository.AvailabilityRepository;
import pe.edu.bikeswap.bike_service.domain.service.AvailabilityService;
import pe.edu.bikeswap.bike_service.domain.service.BikeService;
import pe.edu.bikeswap.bike_service.entity.AvailabilityEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final BikeService bikeService;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, @Lazy BikeService bikeService) {
        this.availabilityRepository = availabilityRepository;
        this.bikeService = bikeService;
    }

    @Override
    public Availability create(AvailabilityDto availabilityDto) {
        validateAvailability(availabilityDto);
        existsBicycle(availabilityDto.getBicycleId());
        Availability availability = AvailabilityMapper.INSTANCE.availabilityDtoToModel(availabilityDto);
        AvailabilityEntity availabilityEntity = availabilityRepository.save(AvailabilityMapper.INSTANCE.availabilityModelToEntity(availability));
        return AvailabilityMapper.INSTANCE.availabilityEntityToModel(availabilityEntity);
    }

    @Override
    public Availability getById(Long availability_id) {
        existsAvailability(availability_id);
        AvailabilityEntity availabilityEntity = availabilityRepository.findById(availability_id).orElse(null);
        return AvailabilityMapper.INSTANCE.availabilityEntityToModel(availabilityEntity);
    }

    @Override
    public void delete(Long availability_id) {
        existsAvailability(availability_id);
        availabilityRepository.deleteById(availability_id);
    }

    @Override
    public boolean existsBetweenDates(Long bicycle_id, LocalDate availability_start_date, LocalDate availability_end_date) {
        existsBicycle(bicycle_id);
        return availabilityRepository.existsByBicycleIdAndAvailabilityStartDateLessThanEqualAndAvailabilityEndDateGreaterThanEqual(bicycle_id, availability_start_date, availability_end_date);
    }

    @Override
    public List<Availability> getByBikeId(Long bicycle_id) {
        existsBicycle(bicycle_id);
        List<AvailabilityEntity> availabilityEntities = availabilityRepository.findByBicycleId(bicycle_id);
        return AvailabilityMapper.INSTANCE.availabilityEntityListToModelList(availabilityEntities);
    }

    @Override
    public List<Availability> getByBicycleIdAndAvailabilityType(Long bicycle_id, boolean availability_type) {
        existsBicycle(bicycle_id);
        List<AvailabilityEntity> availabilityEntities = availabilityRepository.findByBicycleIdAndAvailabilityType(bicycle_id, availability_type);
        return AvailabilityMapper.INSTANCE.availabilityEntityListToModelList(availabilityEntities);
    }

    private void existsAvailability (Long availability_id) {
        if (!availabilityRepository.existsById(availability_id))
            throw new ResourceNotFoundException("Availability with id " + availability_id + " does not exist");
    }

    private void existsBicycle(Long bicycle_id) {
        Optional<Bike> bicycle = Optional.of(bikeService.getBikeById(bicycle_id));
        if (bicycle.isEmpty())
            throw new ResourceNotFoundException("Bicycle with id " + bicycle_id + " does not exist");
    }


    private void validateAvailability(AvailabilityDto availability) {
        if (availability.getAvailabilityStartDate().isAfter(availability.getAvailabilityEndDate()))
            throw new ValidationException("Availability start date must be before availability end date");
        if (availability.getAvailabilityStartDate().isBefore(LocalDate.now()))
            throw new ValidationException("Availability start date must be after today");
        if (availability.getBicycleId() == null)
            throw new ValidationException("Bicycle id must not be null");
    }
}

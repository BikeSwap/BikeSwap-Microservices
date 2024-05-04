package pe.edu.bikeswap.bike_service.application.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import pe.edu.bikeswap.bike_service.application.exception.ResourceNotFoundException;
import pe.edu.bikeswap.bike_service.application.exception.ValidationException;
import pe.edu.bikeswap.bike_service.application.mapper.BikeMapper;
import pe.edu.bikeswap.bike_service.domain.model.Availability;
import pe.edu.bikeswap.bike_service.domain.model.Bike;
import pe.edu.bikeswap.bike_service.domain.repository.BikeRepository;
import pe.edu.bikeswap.bike_service.domain.service.AvailabilityService;
import pe.edu.bikeswap.bike_service.domain.service.BikeService;
import pe.edu.bikeswap.bike_service.entity.BikeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;
    private final AvailabilityService availabilityService;

    public BikeServiceImpl(BikeRepository bikeRepository, AvailabilityService availabilityRepository) {
        this.bikeRepository = bikeRepository;
        this.availabilityService = availabilityRepository;
    }

    @Override
    public Bike createBike(Bike bike) {
        validateBicycle(bike);
        BikeEntity bikeEntity = BikeMapper.INSTANCE.bicycleModelToEntity(bike);
        return BikeMapper.INSTANCE.bicycleEntityToModel(bikeRepository.save(bikeEntity));
    }

    @Override
    public Bike getBikeById(Long bicycle_id) {
        existsBicycleByBicycleId(bicycle_id);
        BikeEntity bikeEntity = bikeRepository.findById(bicycle_id).orElse(null);
        return BikeMapper.INSTANCE.bicycleEntityToModel(bikeEntity);
    }

    @Override
    public Bike updateBike(Long bicycleId, Bike bike) {
        existsBicycleByBicycleId(bicycleId);
        bike.setId(bicycleId);
        BikeEntity bikeEntity = BikeMapper.INSTANCE.bicycleModelToEntity(bike);
        bikeRepository.save(bikeEntity);
        return bike;
    }

    @Override
    public void deleteBike(Long bicycle_id) {
        existsBicycleByBicycleId(bicycle_id);
        bikeRepository.deleteById(bicycle_id);
    }

    @Override
    public List<Bike> getAllBikes() {
        List<BikeEntity> bicycleEntities = bikeRepository.findAll();
        return BikeMapper.INSTANCE.bicycleEntityListToModelList(bicycleEntities);
    }

    @Override
    public List<Bike> getAllAvailableBikes(LocalDate start_date, LocalDate end_date) {
        List<Bike> bikes = new ArrayList<>();
        for (Bike bike : BikeMapper.INSTANCE.bicycleEntityListToModelList(bikeRepository.findAll())) {
            System.out.println("bicycle id: " + bike.getId());
            List<Availability> availabilities = availabilityService.getByBikeId(bike.getId());
            boolean isAvailable = true;
            for (Availability availability: availabilities) {
                if (availability.getAvailabilityStartDate().equals(start_date) || availability.getAvailabilityEndDate().equals(start_date) ||
                        availability.getAvailabilityStartDate().equals(end_date) || availability.getAvailabilityEndDate().equals(end_date)) {
                    isAvailable = false;
                    break;
                }

                if (availability.getAvailabilityStartDate().isAfter(end_date) || availability.getAvailabilityEndDate().isBefore(start_date))
                    continue;

                if (availability.getAvailabilityStartDate().isBefore(start_date) && availability.getAvailabilityEndDate().isAfter(end_date)){
                    isAvailable = false;
                    break;
                }

                if (availability.getAvailabilityStartDate().isBefore(start_date) && availability.getAvailabilityEndDate().isBefore(end_date)){
                    isAvailable = false;
                    break;
                }

                if (availability.getAvailabilityStartDate().isAfter(start_date) && availability.getAvailabilityEndDate().isAfter(end_date)){
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable)
                bikes.add(bike);
        }
        return bikes;
    }

    private void existsBicycleByBicycleId(Long bicycleId) {
        if (!bikeRepository.existsById(bicycleId)) {
            throw new ResourceNotFoundException("No existe la bicicleta con el id: " + bicycleId);
        }
    }
    @SneakyThrows
    private void validateBicycle(Bike bike) {
        if (bike.getBicycleName() == null || bike.getBicycleName().isEmpty()) {
            throw new ValidationException("El nombre de la bicicleta debe ser obligatorio");
        }
        if (bike.getBicycleName().length() > 50) {
            throw new ValidationException("El nombre de la bicicleta no debe exceder los 50 caracteres");
        }
        if (bike.getBicycleDescription() == null || bike.getBicycleDescription().isEmpty()) {
            throw new ValidationException("La descripción de la bicicleta debe ser obligatoria");
        }
        if (bike.getBicycleDescription().length() > 200) {
            throw new ValidationException("La descripción de la bicicleta no debe exceder los 200 caracteres");
        }
        if (bike.getBicyclePrice() == 0) {
            throw new ValidationException("El precio de la bicicleta debe ser obligatorio");
        }
        if (bike.getBicyclePrice() < 0) {
            throw new ValidationException("El precio de la bicicleta no debe ser negativo");
        }
        if (bike.getBicycleSize() == null || bike.getBicycleSize().isEmpty()) {
            throw new ValidationException("El tamaño de la bicicleta debe ser obligatorio");
        }
    }
}

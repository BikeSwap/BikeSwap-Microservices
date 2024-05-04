package pe.edu.bikeswap.bike_service.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.edu.bikeswap.bike_service.application.dto.AvailabilityDto;
import pe.edu.bikeswap.bike_service.domain.model.Availability;
import pe.edu.bikeswap.bike_service.entity.AvailabilityEntity;

import java.util.List;

@Mapper
public interface AvailabilityMapper {
    AvailabilityMapper INSTANCE = Mappers.getMapper(AvailabilityMapper.class);
    Availability availabilityDtoToModel(AvailabilityDto availabilityDto);
    AvailabilityEntity availabilityModelToEntity(Availability availability);

    Availability availabilityEntityToModel(AvailabilityEntity availabilityEntity);

    List<Availability> availabilityEntityListToModelList(List<AvailabilityEntity> availabilityEntities);
}

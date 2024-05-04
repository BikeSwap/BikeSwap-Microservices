package pe.edu.bikeswap.bike_service.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pe.edu.bikeswap.bike_service.domain.model.Bike;
import pe.edu.bikeswap.bike_service.entity.BikeEntity;

import java.util.List;

@Mapper
public interface BikeMapper {
    BikeMapper INSTANCE = Mappers.getMapper(BikeMapper.class);
    BikeEntity bicycleModelToEntity(Bike bike);

    Bike bicycleEntityToModel(BikeEntity bikeEntity);

    List<Bike> bicycleEntityListToModelList(List<BikeEntity> bicycleEntities);
}

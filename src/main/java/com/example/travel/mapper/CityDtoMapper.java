package com.example.travel.mapper;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CityDtoMapper {
    CityDtoMapper INSTANCE = Mappers.getMapper(CityDtoMapper.class);
    @Mapping(target="id", ignore = true)
    @Mapping(target="travels", ignore = true)
    @Mapping(target="lookAt", ignore = true)
    City toEntity(CityDto cityDto);


    CityDto toDto(City city);
}

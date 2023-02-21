package com.example.travel.mapper;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface CityDtoMapper {
    City toEntity(CityDto cityDto);

    CityDto toDto(City city);
}

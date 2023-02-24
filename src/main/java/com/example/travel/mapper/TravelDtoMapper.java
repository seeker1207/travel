package com.example.travel.mapper;

import com.example.travel.domain.travel.dto.TravelDto;
import com.example.travel.domain.travel.Travel;
import com.example.travel.domain.travel.dto.TravelResponse;
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
public interface TravelDtoMapper {

    TravelDtoMapper INSTANCE = Mappers.getMapper(TravelDtoMapper.class);

    @Mapping(target="id", ignore = true)
    @Mapping(target="cities", ignore = true)
    @Mapping(target="traveler", ignore = true)
    Travel toEntity(TravelDto travelDto);

    @Mapping(target="userEmail", ignore = true)
    @Mapping(target="cityIds", ignore = true)
    TravelDto toDto(Travel travel);

    @Mapping(target="cities",
            expression = "java(" +
                    "travel.getCities().stream()" +
                    ".map((cityTravel) -> CityDtoMapper.INSTANCE.toResponse(cityTravel.getCity()))" +
                    ".toList()" +
                    ")")
    @Mapping(target="userId", expression = "java(travel.getTraveler().getId())")
    TravelResponse toResponse(Travel travel);

}

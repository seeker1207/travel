package com.example.travel.mapper;

import com.example.travel.domain.travel.TravelDto;
import com.example.travel.domain.travel.Travel;
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
    @Mapping(target="cityId", ignore = true)
    TravelDto toDto(Travel travel);
}

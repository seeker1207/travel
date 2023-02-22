package com.example.travel.mapper;

import com.example.travel.domain.travel.TravelDto;
import com.example.travel.domain.travel.Travel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TravelDtoMapper {


    @Mapping(target="id", ignore = true)
    Travel toEntity(TravelDto travelDto);

    @Mapping(target="userEmail", ignore = true)
    TravelDto toDto(Travel travel);
}

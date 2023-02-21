package com.example.travel.domain.city;

import com.example.travel.mapper.CityDtoMapper;
import org.mapstruct.factory.Mappers;

import java.util.NoSuchElementException;

public class CityService {
    private final CityDtoMapper cityDtoMapper = Mappers.getMapper(CityDtoMapper.class);
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void saveCity(CityDto cityDto) {
        City city = cityDtoMapper.toEntity(cityDto);
        cityRepository.save(city);
    }

    public void updateCity(Long cityId, CityDto cityDto) {
        City city = cityRepository.findById(cityId).orElseThrow(NoSuchElementException::new);
        city.allUpdate(cityDto);
        cityRepository.save(city);
    }

    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(NoSuchElementException::new);
    }

    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }
}

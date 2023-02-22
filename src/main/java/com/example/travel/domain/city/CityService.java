package com.example.travel.domain.city;

import com.example.travel.mapper.CityDtoMapper;

import java.util.NoSuchElementException;

public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void saveCity(CityDto cityDto) {
        City city = CityDtoMapper.INSTANCE.toEntity(cityDto);
        cityRepository.save(city);
    }

    public void updateCity(Long cityId, CityDto cityDto) {
        City city = cityRepository.findById(cityId).orElseThrow(NoSuchElementException::new);
        city.update(cityDto);
        cityRepository.save(city);
    }

    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(NoSuchElementException::new);
    }

    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }
}

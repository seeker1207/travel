package com.example.travel.domain.city;

import com.example.travel.domain.user.MyUserRepository;
import com.example.travel.mapper.CityDtoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CityService {
    private final CityRepository cityRepository;

    private final MyUserRepository userRepository;

    public CityService(CityRepository cityRepository, MyUserRepository userRepository) {
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
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
        City city = cityRepository.findById(cityId).orElseThrow(NoSuchElementException::new);
        city.updateLookAtTime();
        return city;
    }

    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    public List<City> getTravelingCitiesByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<City> cities = cityRepository.findTravelingCitiesByUser(userId);
        cities.forEach(City::updateLookAtTime);
        return cities;
    }
    public List<City> getWillTravelCitiesByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<City> cities = cityRepository.findWillTravelCitiesByUser(userId);
        cities.forEach(City::updateLookAtTime);
        return cities;
    }

    public List<City> getCitiesSaveInOneDayByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<City> cities = cityRepository.findTop10ByCreatedAtBetween(userId,
                LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L));
        cities.forEach(City::updateLookAtTime);
        return cities;
    }

    public List<City> getCitiesLookAtForOneWeekByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<City> cities = cityRepository.findTop10ByLookAtBetween(userId,
                LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0)),
                LocalDateTime.now());
        cities.forEach(City::updateLookAtTime);
        return cities;
    }


}

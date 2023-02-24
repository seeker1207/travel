package com.example.travel.domain.travel;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityRepository;
import com.example.travel.domain.travel.dto.TravelDto;
import com.example.travel.domain.travel.dto.TravelUpdateDto;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import com.example.travel.mapper.TravelDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TravelService {
    private final TravelRepository travelRepository;
    private final MyUserRepository myUserRepository;
    private final CityRepository cityRepository;
    private final TravelDtoMapper travelDtoMapper = TravelDtoMapper.INSTANCE;


    public TravelService(TravelRepository travelRepository, MyUserRepository myUserRepository, CityRepository cityRepository) {
        this.travelRepository = travelRepository;
        this.myUserRepository = myUserRepository;
        this.cityRepository = cityRepository;
    }

    public void makeTravel(TravelDto travelDto) {
        MyUser currentUser = myUserRepository.findByEmail(travelDto.getUserEmail()).orElseThrow(NoSuchElementException::new);
        List<City> targetCities = travelDto.getCityIds().stream()
                .map((cityId) -> cityRepository.findById(Long.valueOf(cityId))
                        .orElseThrow(NoSuchElementException::new))
                .toList();


        Travel travel = travelDtoMapper.toEntity(travelDto);
        travel.setTraveler(currentUser);
        travel.addCity(targetCities);

        travelRepository.save(travel);
    }

    public Travel getTravel(Long travelId) {
        return travelRepository.findById(travelId).orElseThrow(NoSuchElementException::new);
    }

    public void updateTravel(Long travelId, TravelUpdateDto travelDto) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(NoSuchElementException::new);
        travel.updateByDto(travelDto);

        travelRepository.save(travel);
    }

    public void deleteTravel(Long travelId) {
        travelRepository.deleteById(travelId);
    }

}

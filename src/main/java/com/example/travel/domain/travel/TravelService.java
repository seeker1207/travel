package com.example.travel.domain.travel;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityRepository;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import com.example.travel.mapper.TravelDtoMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TravelService {
    private final TravelRepository travelRepository;
    private final MyUserRepository myUserRepository;
    private final CityRepository cityRepository;

    public TravelService(TravelRepository travelRepository, MyUserRepository myUserRepository, CityRepository cityRepository) {
        this.travelRepository = travelRepository;
        this.myUserRepository = myUserRepository;
        this.cityRepository = cityRepository;
    }

    public void makeTravelPlan(TravelDto travelDto) {
        MyUser currentUser = myUserRepository.findByEmail(travelDto.getUserEmail()).orElseThrow(NoSuchElementException::new);
        City targetCity = cityRepository.findById(Long.valueOf(travelDto.getCityId())).orElseThrow(NoSuchElementException::new);
        Travel newTravel = TravelDtoMapper.INSTANCE.toEntity(travelDto);

        newTravel.setTraveler(currentUser);
        newTravel.addCity(targetCity);

        travelRepository.save(newTravel);
    }

    public Travel getTravel(Long id) {
        return travelRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void updateTravelPlan(Long id, TravelUpdateDto travelUpdateDto) {
        Travel targetTravel = travelRepository.findById(id).orElseThrow(NoSuchElementException::new);
        targetTravel.updateByDto(travelUpdateDto);
    }

    public void deleteTravel(Long id) {
        travelRepository.deleteById(id);
    }


}

package com.example.travel.controller;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityService;
import com.example.travel.domain.city.dto.CityDto;
import com.example.travel.domain.city.dto.CityResponse;
import com.example.travel.mapper.CityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;
    private final CityDtoMapper cityDtoMapper = CityDtoMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<CityResponse> saveCity(@RequestBody CityDto cityDto) {
        if (ObjectUtils.isEmpty(cityDto.getCityName())) {
            ResponseEntity.badRequest().build();
        }
        if (ObjectUtils.isEmpty(cityDto.getDescription())) {
            cityDto.setDescription("");
        }

        cityService.saveCity(cityDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CityResponse> getCity(@PathVariable String id) {
        City city = cityService.getCity(Long.valueOf(id));

        return ResponseEntity.ok(cityDtoMapper.toResponse(city));
    }

    @PatchMapping("{id}")
    public ResponseEntity<CityResponse> updateCity(@PathVariable String id, @RequestBody CityDto cityDto) {
        cityService.updateCity(Long.valueOf(id), cityDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CityResponse> deleteCity(@PathVariable String id) {
        cityService.deleteCity(Long.valueOf(id));

        return ResponseEntity.ok().build();
    }

    @GetMapping("traveling")
    public ResponseEntity<List<CityResponse>> getTravellingCity(@RequestParam("userId") String userId) {
        List<City> cities = cityService.getTravelingCitiesByUser(Long.valueOf(userId));
        return ResponseEntity.ok(cities.stream().map(CityDtoMapper.INSTANCE::toResponse).toList());
    }

    @GetMapping("willtravel")
    public ResponseEntity<List<CityResponse>> getWillTravelCity(@RequestParam("userId") String userId) {
        List<City> cities = cityService.getWillTravelCitiesByUser(Long.valueOf(userId));
        return ResponseEntity.ok(cities.stream().map(CityDtoMapper.INSTANCE::toResponse).toList());
    }

    @GetMapping("save-oneday")
    public ResponseEntity<List<CityResponse>> getCitiesSaveInOneDay(@RequestParam("userId") String userId) {
        List<City> cities = cityService.getCitiesSaveInOneDayByUser(Long.valueOf(userId));
        return ResponseEntity.ok(cities.stream().map(CityDtoMapper.INSTANCE::toResponse).toList());
    }

    @GetMapping("lookat-oneweek")
    public ResponseEntity<List<CityResponse>> getCitiesLookAtForOneWeek(@RequestParam("userId") String userId) {
        List<City> cities = cityService.getCitiesLookAtForOneWeekByUser(Long.valueOf(userId));
        return ResponseEntity.ok(cities.stream().map(CityDtoMapper.INSTANCE::toResponse).toList());
    }
}

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
}

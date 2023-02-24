package com.example.travel.controller;

import com.example.travel.domain.travel.*;
import com.example.travel.domain.travel.dto.TravelDto;
import com.example.travel.domain.travel.dto.TravelResponse;
import com.example.travel.domain.travel.dto.TravelUpdateDto;
import com.example.travel.mapper.TravelDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {
    private final TravelService travelService;

    private final TravelDtoMapper travelDtoMapper = TravelDtoMapper.INSTANCE;

    @PostMapping
    public ResponseEntity<TravelDto> makeTravel(@RequestBody TravelDto travelDto) {
        if (ObjectUtils.isEmpty(travelDto.getTravelTitle())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(travelDto.getUserEmail())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(travelDto.getCityIds())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(travelDto.getStartDate())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(travelDto.getEndDate())) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate startDate = travelDto.getStartDate();
        LocalDate endDate = travelDto.getEndDate();
        if (!startDate.isEqual(endDate) && startDate.isBefore(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        travelService.makeTravel(travelDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TravelResponse> getTravel(@PathVariable String id) {
        Travel travel = travelService.getTravel(Long.valueOf(id));

        return ResponseEntity.ok(travelDtoMapper.toResponse(travel));
    }

    @PatchMapping("{id}")
    public ResponseEntity<TravelResponse> updateTravel(
            @PathVariable String id, @RequestBody TravelUpdateDto travelUpdateDto) {
        if (ObjectUtils.isEmpty(travelUpdateDto.getTravelTitle())) {
            return ResponseEntity.badRequest().build();        }
        if (ObjectUtils.isEmpty(travelUpdateDto.getStartDate())) {
            return ResponseEntity.badRequest().build();
        }

        travelService.updateTravel(Long.valueOf(id), travelUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TravelResponse> deleteTravel(@PathVariable String id){

        travelService.deleteTravel(Long.valueOf(id));
        return ResponseEntity.ok().build();
    }
}

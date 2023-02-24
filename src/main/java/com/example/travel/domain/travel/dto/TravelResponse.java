package com.example.travel.domain.travel.dto;

import com.example.travel.domain.city.dto.CityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelResponse {
    private List<CityResponse> cities;
    private String travelTitle;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
}

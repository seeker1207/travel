package com.example.travel.domain.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelUpdateDto {
    private String travelTitle;
    private LocalDate startDate;
    private LocalDate endDate;
}

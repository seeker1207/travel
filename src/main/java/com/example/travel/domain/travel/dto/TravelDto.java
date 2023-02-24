package com.example.travel.domain.travel.dto;

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
public class TravelDto {
    private String userEmail;
    private List<String> cityIds;
    private String travelTitle;
    private LocalDate startDate;
    private LocalDate endDate;

}

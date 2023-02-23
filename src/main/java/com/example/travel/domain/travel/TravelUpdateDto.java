package com.example.travel.domain.travel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelUpdateDto {
    private String travelTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

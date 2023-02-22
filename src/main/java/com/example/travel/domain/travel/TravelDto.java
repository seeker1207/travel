package com.example.travel.domain.travel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelDto {
    private String userEmail;
    private String cityId;
    private String travelTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}

package com.example.travel.domain.citytravel;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.city.City;
import com.example.travel.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CityTravel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Travel travel;

    @ManyToOne
    private City city;

    @Builder
    public CityTravel(Travel travel, City city) {
        this.travel = travel;
        this.city = city;
    }
}

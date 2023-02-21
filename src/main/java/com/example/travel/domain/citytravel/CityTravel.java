package com.example.travel.domain.citytravel;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.city.City;
import com.example.travel.domain.travel.Travel;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CityTravel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Travel travel;

    @ManyToOne
    private City city;

}

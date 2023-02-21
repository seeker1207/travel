package com.example.travel.domain.city;


import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.citytravel.CityTravel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class City extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String cityName;

    private String desc;

    @OneToMany(mappedBy = "city")
    @Builder.Default
    private List<CityTravel> travels = new ArrayList<>();

    public void modifyCityName(String cityName) {
        this.cityName = cityName;
    }

    public void modifyCityDesc(String desc) {
        this.desc = desc;
    }

    public void modifyCityNameAndDesc(String cityName, String desc) {
        modifyCityName(cityName);
        modifyCityDesc(desc);
    }

    public void allUpdate(CityDto cityDto) {
        this.cityName = cityDto.getCityName();
        this.desc = cityDto.getDesc();

    }
}

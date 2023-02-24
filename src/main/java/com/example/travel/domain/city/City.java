package com.example.travel.domain.city;


import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.city.dto.CityDto;
import com.example.travel.domain.citytravel.CityTravel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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

    private String description;

    private LocalDateTime lookAt;

    @OneToMany(mappedBy = "city", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CityTravel> travels = new ArrayList<>();

    public void modifyCityName(String cityName) {
        this.cityName = cityName;
    }

    public void modifyCityDesc(String desc) {
        this.description = desc;
    }

    public void modifyCityNameAndDesc(String cityName, String desc) {
        modifyCityName(cityName);
        modifyCityDesc(desc);
    }

    public void allUpdate(CityDto cityDto) {
        this.cityName = cityDto.getCityName();
        this.description = cityDto.getDescription();

    }

    public void update(CityDto cityDto) {
        if (!ObjectUtils.isEmpty(cityDto.getCityName())) {
            this.cityName = cityDto.getCityName();
        }

        if (!ObjectUtils.isEmpty(cityDto.getDescription())) {
            this.description = cityDto.getDescription();
        }
    }

    public void updateLookAtTime() {
        this.lookAt = LocalDateTime.now();
    }
}

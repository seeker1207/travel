package com.example.travel.domain.travel;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.city.City;
import com.example.travel.domain.citytravel.CityTravel;
import com.example.travel.domain.user.MyUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class Travel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String travelTitle;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    @OneToMany(mappedBy = "travel")
    @Cascade(CascadeType.ALL)
    @Builder.Default
    private List<CityTravel> cities = new ArrayList<>();

    @ManyToOne
    private MyUser traveler;

    public void modifyTitle(String title) {
        this.travelTitle = title;
    }
    public void setTraveler(MyUser traveler) {
        this.traveler = traveler;
    }

    public void addCity(City city) {
        CityTravel cityTravel = new CityTravel(this, city);
        cities.add(cityTravel);
        city.getTravels().add(cityTravel);
    }

    public void updateByDto(TravelUpdateDto travelUpdateDto) {
        if (travelUpdateDto.getTravelTitle() != null) {
            travelTitle = travelUpdateDto.getTravelTitle();
        }
        if (travelUpdateDto.getStartDate() != null) {
            startDate = travelUpdateDto.getStartDate();
        }
        if (travelUpdateDto.getEndDate() != null) {
            endDate = travelUpdateDto.getEndDate();
        }
    }


}

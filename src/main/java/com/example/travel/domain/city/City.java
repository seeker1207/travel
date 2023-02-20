package com.example.travel.domain.city;


import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.travelItem.TravelItem;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class City extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String cityName;

    private String desc;

    @OneToMany(mappedBy = "city")
    private List<TravelItem> travelItems = new ArrayList<>();


}

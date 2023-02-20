package com.example.travel.domain.travel;

import com.example.travel.domain.BaseEntity;
import com.example.travel.domain.city.City;
import com.example.travel.domain.travelItem.TravelItem;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Travel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @OneToMany(mappedBy = "travel")
    private List<TravelItem> travelItems = new ArrayList<>();


}

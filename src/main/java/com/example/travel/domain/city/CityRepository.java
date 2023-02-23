package com.example.travel.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String CityName);

    @Query("select city  from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and current date >= travel.startDate and current date <= travel.endDate " +
            "order by travel.startDate"
    )
    Optional<List<City>> findTravelingCitiesByUser(Long userId);

    @Query("select distinct city, min(travel.startDate) from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and local_date < travel.startDate " +
            "order by min(travel.startDate) asc limit 10"
    )
    Optional<List<City>> findWillTravelCitiesByUser(Long userId);

}

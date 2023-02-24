package com.example.travel.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String CityName);

    @Query("select city  from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and local_date >= travel.startDate and local_date <= travel.endDate " +
            "order by travel.startDate"
    )
    List<City> findTravelingCitiesByUser(Long userId);

    @Query("select distinct city, travel.startDate from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and local_date < travel.startDate " +
            "order by travel.startDate asc limit 10"
    )
    List<City> findWillTravelCitiesByUser(Long userId);

    @Query("select distinct city, travel.createdAt from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and local_datetime >= :start and local_datetime < :end " +
            "order by travel.createdAt desc limit 10")
    List<City> findTop10ByCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    @Query("select distinct city, travel.createdAt from City city " +
            "join city.travels cityTravel " +
            "join cityTravel.travel travel " +
            "where travel.traveler.id = :userId " +
            "and local_datetime >= :start and local_datetime < :end " +
            "order by travel.createdAt desc limit 10")
    List<City> findTop10ByLookAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

//    Optional<List<City>> findExceptCondition();
}

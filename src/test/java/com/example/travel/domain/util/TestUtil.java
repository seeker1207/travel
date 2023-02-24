package com.example.travel.domain.util;

import com.example.travel.domain.city.City;
import com.example.travel.domain.travel.Travel;
import com.example.travel.domain.user.MyUser;

import java.time.LocalDate;

public class TestUtil {
    static public City getCity() {
        return City.builder().id(1L).cityName("부산").description("바다가 아름다운 도시입니다.").build();
    }

    static public Travel getTravel() {
        return Travel.builder()
                .travelTitle("졸업 기념 여행")
                .startDate(LocalDate.of(2023, 2, 1))
                .endDate(LocalDate.of(2023, 2, 15))
                .build();
    }

    static public MyUser getUser() {
        return MyUser.builder().email("test@email.com").nickname("여행덕후").build();
    }

}

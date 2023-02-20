package com.example.travel.domain.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class CityRepositoryTest {
    @Autowired
    private CityRepository cityRepository;

    private final String cityName = "부산";
    private final String desc = "부산은 바다가 아름다운 도시입니다.";

    private City initCity;

    @BeforeEach
    void setUp() {
        initCity = City.builder().cityName(cityName).desc(desc).build();
        cityRepository.save(initCity);
    }

    @Test
    void 도시_정보가_저장된다() {
        // given
        String cityName = "서울";
        String desc = "서울은 살기 좋은 도시입니다.";
        City newCity = City.builder().cityName(cityName).desc(desc).build();

        // when
        cityRepository.save(newCity);

        // then
        assertEquals(cityName, newCity.getCityName());
        assertEquals(desc, newCity.getDesc());
    }

    @Test
    void 도시_정보가_조회된다() {
        //given
        //when
        City city = cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new);
        //then
        assertEquals(city.getCityName(), cityName);
        assertEquals(city.getDesc(), desc);

    }

    @Test
    void 도시_정보가_수정된다() {
        // given
        City city = cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new);
        String newCityName = "인천";
        String newDesc = "인천은 야경이 아름다운 도시입니다.";
        // when
        city.modifyCityNameAndDesc(newCityName, newDesc);
        cityRepository.save(city);

        // then
        assertEquals(city.getCityName(), newCityName);
        assertEquals(city.getDesc(), newDesc);
    }

    @Test
    void 도시_정보가_삭제된다() {
        // given
        // when
        cityRepository.delete(initCity);
        // then
        assertThrows(NoSuchElementException.class,
                () -> cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new)
        );

    }

}
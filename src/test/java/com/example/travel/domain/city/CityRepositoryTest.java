package com.example.travel.domain.city;

import com.example.travel.domain.travel.Travel;
import com.example.travel.domain.travel.TravelRepository;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("test")
class CityRepositoryTest {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private TravelRepository travelRepository;

    private final String cityName = "부산";
    private final String desc = "부산은 바다가 아름다운 도시입니다.";

    @Test
    void 도시_정보가_저장된다() {
        // given
        String cityName = "서울";
        String desc = "서울은 살기 좋은 도시입니다.";
        City newCity = City.builder().cityName(cityName).description(desc).build();

        // when
        cityRepository.save(newCity);
        City savedCity = cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new);

        // then
        assertEquals(cityName, savedCity.getCityName());
        assertEquals(desc, savedCity.getDescription());
    }

    @Test
    void 단일_도시_정보가_조회된다() {
        //given
        City savedCity = getCity(cityName, desc);
        cityRepository.save(savedCity);
        //when
        City city = cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new);
        //then
        assertEquals(city.getCityName(), cityName);
        assertEquals(city.getDescription(), desc);

    }

    @Test
    void 도시_정보가_수정된다() {
        // given
        City savedCity = getCity(cityName, desc);
        cityRepository.save(savedCity);

        City city = cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new);
        String newCityName = "인천";
        String newDesc = "인천은 야경이 아름다운 도시입니다.";
        // when
        city.modifyCityNameAndDesc(newCityName, newDesc);
        cityRepository.save(city);

        City updatedCity = cityRepository.findByCityName(newCityName).orElseThrow(NoSuchElementException::new);

        // then
        assertEquals(updatedCity.getCityName(), newCityName);
        assertEquals(updatedCity.getDescription(), newDesc);
    }

    @Test
    void 도시_정보가_삭제된다() {
        // given
        City targetCity = getCity(cityName, desc);
        cityRepository.save(targetCity);
        // when
        cityRepository.delete(targetCity);
        // then
        assertThrows(NoSuchElementException.class,
                () -> cityRepository.findByCityName(cityName).orElseThrow(NoSuchElementException::new)
        );

    }

    @Test
    void 현재_여행중인_도시정보를_조회한다() {
        //given
        List<Travel> travels = makeAndSaveTravels();

        //when
        List<City> cities = cityRepository.findTravelingCitiesByUser(travels.get(0).getTraveler().getId());

        //then
        assertEquals("여행 중인 도시", cities.get(0).getCityName());
    }

    @Test
    void 여행이_예정된_도시를_조회한다() {
        //given
        List<Travel> travels = makeAndSaveTravels();

        //when
        List<City> cities = cityRepository.findWillTravelCitiesByUser(travels.get(0).getTraveler().getId());

        //then
        assertEquals("여행 예정인 도시", cities.get(0).getCityName());
    }

    @Test
    void 날짜_범위_안에_등록된_도시를_조회한다() {
        //given
        List<Travel> travels = makeAndSaveTravels();

        //when
        List<City> cities = cityRepository.findTop10ByCreatedAtBetween(travels.get(0).getTraveler().getId(),
                LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L));

        //then
        assertEquals(3, cities.size());
    }

    @Test
    void 날짜_범위_안에_한_번_이상_조회된_도시를_조회한다() {
        List<Travel> travels = makeAndSaveTravels();

        cityRepository.findById(1L);

        List<City> cities = cityRepository.findTop10ByLookAtBetween(travels.get(0).getTraveler().getId(),
                LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.of(0, 0)),
                LocalDateTime.now());

        assertEquals(0, cities.size());
    }

    private City getCity(String cityName, String desc) {
        return City.builder().cityName(cityName).description(desc).build();
    }

    private MyUser getUser() {
        return MyUser.builder().email("test@travel.com").password("1234").nickname("여행덕후").build();
    }

    private List<Travel> makeAndSaveTravels() {
        Travel travelOne = Travel.builder()
                .travelTitle("여행 중인 일정")
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now().plusDays(3))
                .build();

        Travel travelTwo = Travel.builder()
                .travelTitle("여행 예정인 일정")
                .startDate(LocalDate.now().plusDays(3))
                .endDate(LocalDate.now().plusDays(5))
                .build();

        Travel travelThree = Travel.builder()
                .travelTitle("여행이 끝난 일정")
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now().minusDays(1))
                .build();

        List<Travel> travels = List.of(travelOne, travelTwo, travelThree);

        City cityOne = getCity("여행 중인 도시", "여행중인 도시입니다.");
        City cityTwo = getCity("여행 예정인 도시", "여행예정인 도시입니다.");
        City cityThree = getCity("여행이 끝난 도시", "여행이 끝난 도시입니다.");

        List<City> cities = List.of(cityOne, cityTwo, cityThree);

        MyUser myUser = getUser();
        myUserRepository.save(myUser);

        cityRepository.saveAll(cities);
        travelOne.addCity(cityOne);
        travelTwo.addCity(cityTwo);
        travelThree.addCity(cityThree);

        travels.forEach((travel) -> travel.setTraveler(myUser));

        travelRepository.saveAll(travels);

        return travels;
    }
}
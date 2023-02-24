package com.example.travel.domain.city;

import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @Mock
    private MyUserRepository userRepository;

    @InjectMocks
    private CityService cityService;

    @Captor
    ArgumentCaptor<City> cityArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    public CityDto getCityDto() {
        return CityDto.builder().cityName("부산").desc("바다가 아름다운 도시입니다.").build();
    }

    public City getCity() {
        return City.builder().id(1L).cityName("부산").desc("바다가 아름다운 도시입니다.").build();
    }

    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        cities.add(getCity());
        cities.add(getCity());
        cities.add(getCity());
        return cities;
    }

    @Test
    public void 도시_정보를_저장한다() {
        //given
        CityDto cityDto = getCityDto();
        City city = getCity();
        when(cityRepository.save(any())).thenReturn(city);
        when(cityRepository.findByCityName("부산")).thenReturn(Optional.ofNullable(getCity()));

        //when
        cityService.saveCity(cityDto);

        //then
        City expectedCity = cityRepository.findByCityName("부산").orElseThrow(NoSuchElementException::new);

        assertEquals(expectedCity.getCityName(), "부산");
        assertEquals(expectedCity.getDesc(), "바다가 아름다운 도시입니다.");
    }

    @Test
    public void 도시_정보를_조회하고_조회된_시간을_저장한다() {
        //given
        City initCity = getCity();
        when(cityRepository.findById(1L)).thenReturn(Optional.of(initCity));

        //when
        City actual = cityService.getCity(1L);


        //then
        assertEquals(actual.getCityName(), initCity.getCityName());
        assertTrue(Objects.nonNull(actual.getLookAt()));
    }

    @Test
    public void 도시_정보를_수정한다() {
        //given
        CityDto cityDto = CityDto.builder().cityName("서울").desc("야경이 아름다운 도시입니다.").build();
        when(cityRepository.save(any())).thenReturn(getCity());
        when(cityRepository.findById(1L)).thenReturn(Optional.of(getCity()));

        //when
        cityService.updateCity(1L, cityDto);
        verify(cityRepository, times(1)).save(cityArgumentCaptor.capture());

        //then
        assertEquals(cityDto.getCityName(), cityArgumentCaptor.getValue().getCityName());
    }

    @Test
    public void 도시_정보를_삭제한다() {
        //given

        //when
        cityService.deleteCity(1L);
        verify(cityRepository, times(1)).deleteById(longArgumentCaptor.capture());

        //then
        assertEquals(1L, longArgumentCaptor.getValue());
    }

    @Test
    public void 회원의_여행중인_도시를_조회한다() {
        //given
        List<City> expectedCities = getCities();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new MyUser()));
        when(cityRepository.findTravelingCitiesByUser(1L)).thenReturn(expectedCities);

        //when
        List<City> cities = cityService.getTravelingCitiesByUser(1L);

        //then
        assertEquals(expectedCities.get(0).getCityName(), cities.get(0).getCityName());
    }

    @Test
    public void 회원의_여행예정인_도시를_조회한다() {
        //given
        List<City> expectedCities = getCities();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new MyUser()));
        when(cityRepository.findWillTravelCitiesByUser(1L)).thenReturn(expectedCities);

        //when
        List<City> cities = cityService.getWillTravelCitiesByUser(1L);

        //then
        assertEquals(expectedCities.get(0).getCityName(), cities.get(0).getCityName());
    }

    @Test
    public void 회원의_하루안에_등록된_도시를_조회한다() {
        //given
        List<City> expectedCities = getCities();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new MyUser()));
        when(cityRepository.findTop10ByCreatedAtBetween(eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(expectedCities);

        //when
        List<City> cities = cityService.getCitiesSaveInOneDayByUser(1L);

        //then
        assertEquals(expectedCities.get(0).getCityName(), cities.get(0).getCityName());
    }

    @Test
    public void 회원의_일주일안에_조회된_도시를_조회한다() {
        //given
        List<City> expectedCities = getCities();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new MyUser()));
        when(cityRepository.findTop10ByLookAtBetween(eq(1L),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        )).thenReturn(expectedCities);

        //when
        List<City> cities = cityService.getCitiesLookAtForOneWeekByUser(1L);

        //then
        assertEquals(expectedCities.get(0).getCityName(), cities.get(0).getCityName());
    }
}
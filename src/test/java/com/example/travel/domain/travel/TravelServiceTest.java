package com.example.travel.domain.travel;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityRepository;
import com.example.travel.domain.travel.dto.TravelDto;
import com.example.travel.domain.travel.dto.TravelUpdateDto;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import com.example.travel.domain.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TravelServiceTest {
    @Mock
    TravelRepository travelRepository;
    @Mock
    MyUserRepository myUserRepository;
    @Mock
    CityRepository cityRepository;

    @InjectMocks
    TravelService travelService;

    @Captor
    ArgumentCaptor<Travel> travelArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    public TravelDto getTravelDto() {
        return TravelDto.builder()
                .travelTitle("졸업 기념 여행")
                .startDate(LocalDate.of(2023, 2, 1))
                .endDate(LocalDate.of(2023, 2, 15))
                .userEmail("test@email.com")
                .cityIds(List.of("1"))
                .build();
    }

    public TravelUpdateDto getTravelUpdateDto() {
        return TravelUpdateDto.builder()
                .travelTitle("졸업 기념 여행")
                .startDate(LocalDate.of(2023, 2, 1))
                .endDate(LocalDate.of(2023, 2, 4))
                .build();
    }

    @Test
    public void 여행_계획을_등록한다() {
        //given
        Travel travel = TestUtil.getTravel();
        TravelDto travelDto = getTravelDto();
        City city = TestUtil.getCity();
        MyUser user = TestUtil.getUser();

        travel.addCity(city);
        travel.setTraveler(user);

        when(myUserRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(travelRepository.findByTravelTitle("졸업 기념 여행")).thenReturn(Optional.of(travel));

        //when
        travelService.makeTravel(travelDto);

        //then
        Travel expectedTravel = travelRepository.findByTravelTitle("졸업 기념 여행").orElseThrow(NoSuchElementException::new);

        assertEquals(expectedTravel.getTravelTitle(), "졸업 기념 여행");
        assertEquals(expectedTravel.getCities().get(0).getCity().getCityName(), "부산");
        assertEquals(expectedTravel.getTraveler().getEmail(), "test@email.com");
    }

    @Test
    public void 여행_계획을_조회한다() {
        //given
        Travel travel = TestUtil.getTravel();
        when(travelRepository.findById(1L)).thenReturn(Optional.of(travel));

        //when
        Travel actualTravel = travelService.getTravel(1L);

        //then
        assertEquals(travel.getTravelTitle(), actualTravel.getTravelTitle());
    }

    @Test
    public void 여행_계획을_수정한다() {
        //given
        Travel travel = TestUtil.getTravel();
        TravelUpdateDto travelDto = getTravelUpdateDto();
        when(travelRepository.findById(1L)).thenReturn(Optional.of(travel));
        //when
        travelService.updateTravel(1L, travelDto);
        verify(travelRepository, times(1)).save(travelArgumentCaptor.capture());

        //then
        assertEquals(travelDto.getTravelTitle(), travelArgumentCaptor.getValue().getTravelTitle());
    }

    @Test
    public void 여행_계획을_삭제한다() {
        //given

        //when
        travelService.deleteTravel(1L);
        verify(travelRepository, times(1)).deleteById(longArgumentCaptor.capture());

        //then
        assertEquals(1L, longArgumentCaptor.getValue());
    }

}
package com.example.travel.domain.travel;

import com.example.travel.domain.city.City;
import com.example.travel.domain.citytravel.CityTravel;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.user.MyUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TravelRepositoryTest {
    @Autowired
    private TravelRepository travelRepository;

    private final String travelTitle = "졸업 기념 여행";
    private final LocalDateTime startDate = LocalDateTime.of(2023, 2, 26, 0, 0);
    private final LocalDateTime endDate = LocalDateTime.of(2023, 3, 1, 0, 0);

    @BeforeEach
    void init() {
        Travel travel = Travel.builder()
                .travelTitle(travelTitle)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        travelRepository.save(travel);
    }

    @Test
    void 여행_정보가_DB에_저장된다() {
        LocalDateTime startDate = LocalDateTime.of(2023, 2, 26, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 3, 1, 0, 0);
        Travel travel = Travel.builder()
                .travelTitle("종강 기념 여행!")
                .startDate(startDate)
                .endDate(endDate)
                .build();
        travelRepository.save(travel);

        List<Travel> savedTravels = travelRepository.findAll();
        assertEquals(savedTravels.size(), 2);
        assertEquals(savedTravels.get(1).getTravelTitle(), "종강 기념 여행!");
        assertEquals(savedTravels.get(1).getStartDate(), startDate);
    }

    @Test
    void DB에_저장된_여행_정보가_조회_된다() {
        List<Travel> travels = travelRepository.findAll();
        LocalDateTime expectedStartDate = LocalDateTime.of(2023, 2, 26, 0, 0);
        assertEquals(travels.size(), 1);
        assertEquals(travels.get(0).getTravelTitle(), "졸업 기념 여행");
        assertEquals(travels.get(0).getStartDate(), expectedStartDate);

    }

    @Test
    void DB에_저장된_여행_정보가_수정_된다() {
        Travel savedTravel = travelRepository.findByTravelTitle(travelTitle).orElseThrow(NoSuchElementException::new);

        savedTravel.modifyTitle("수정된 여행 제목");
        travelRepository.save(savedTravel);

        Travel expectedTravel = travelRepository.findByTravelTitle("수정된 여행 제목").orElseThrow(NoSuchElementException::new);

        assertEquals(expectedTravel.getTravelTitle(), "수정된 여행 제목");
    }

    @Test
    void DB에_저장된_여행_정보가_삭제_된다() {
        Travel savedTravel = travelRepository.findByTravelTitle(travelTitle).orElseThrow(NoSuchElementException::new);

        travelRepository.delete(savedTravel);

        assertThrows(NoSuchElementException.class, () -> {
            travelRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        });
    }




}
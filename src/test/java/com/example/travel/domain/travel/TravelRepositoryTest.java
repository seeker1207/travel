package com.example.travel.domain.travel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
class TravelRepositoryTest {
    @Autowired
    TravelRepository travelRepository;

    final String travelTitle = "졸업 기념 여행";
    final LocalDate startDate = LocalDate.of(2023, 2, 26);
    final LocalDate endDate = LocalDate.of(2023, 3, 1);

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
        LocalDate startDate = LocalDate.of(2023, 2, 26);
        LocalDate endDate = LocalDate.of(2023, 3, 1);
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
        LocalDate expectedStartDate = LocalDate.of(2023, 2, 26);
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
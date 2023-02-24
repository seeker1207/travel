package com.example.travel.controller;

import com.example.travel.domain.travel.Travel;
import com.example.travel.domain.travel.TravelService;
import com.example.travel.domain.travel.dto.TravelDto;
import com.example.travel.domain.travel.dto.TravelUpdateDto;
import com.example.travel.domain.user.MyUser;
import com.example.travel.domain.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TravelController.class)
class TravelControllerTest {

    @MockBean
    TravelService travelService;
    @Autowired
    MockMvc mvc;

    private Travel expected;
    private TravelDto travelDto;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    ArgumentCaptor<TravelUpdateDto> travelUpdateDtoArgumentCaptor;
    @Captor
    ArgumentCaptor<TravelDto> travelDtoArgumentCaptor;

    @BeforeEach
    void setup() {
        expected = TestUtil.getTravel();
        expected.setTraveler(MyUser.builder().build());
        travelDto = TravelDto.builder().travelTitle("졸업 기념 여행")
                .startDate(LocalDate.of(2023, 2, 24))
                .endDate(LocalDate.of(2023, 2, 25))
                .cityIds(List.of("1"))
                .userEmail("test@travel.com")
                .build();
    }
    @Test
    void 여행_계획을_저장한다() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.registerModule(new JavaTimeModule()).writeValueAsString(travelDto);

        mvc.perform(post("/travel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(travelService, times(1))
                .makeTravel(travelDtoArgumentCaptor.capture());
        assertEquals(travelDto.getTravelTitle(), travelDtoArgumentCaptor.getValue().getTravelTitle());

    }

    @Test
    void 여행_계획을_조회한다() throws Exception{
        when(travelService.getTravel(1L)).thenReturn(expected);

        mvc.perform(get("/travel/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.travelTitle").value("졸업 기념 여행"));

    }

    @Test
    void 여행_계획을_수정한다() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.registerModule(new JavaTimeModule()).writeValueAsString(travelDto);

        mvc.perform(patch("/travel/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
        verify(travelService, times(1))
                .updateTravel(longArgumentCaptor.capture(), travelUpdateDtoArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
        assertEquals(travelDto.getTravelTitle(), travelUpdateDtoArgumentCaptor.getValue().getTravelTitle());

    }

    @Test
    void 여행_계획을_삭제한다() throws Exception{

        mvc.perform(delete("/travel/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(travelService, times(1))
                .deleteTravel(longArgumentCaptor.capture());

        assertEquals(1L, longArgumentCaptor.getValue());
    }




}
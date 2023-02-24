package com.example.travel.controller;

import com.example.travel.domain.city.City;
import com.example.travel.domain.city.CityService;
import com.example.travel.domain.city.dto.CityDto;
import com.example.travel.domain.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @MockBean
    CityService cityService;

    @Autowired
    MockMvc mvc;

    private City expected;
    private CityDto cityDto;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    ArgumentCaptor<CityDto> cityDtoArgumentCaptor;

    @BeforeEach
    void setup() {
        expected = TestUtil.getCity();
        cityDto = CityDto.builder().cityName(TestUtil.cityName).description(TestUtil.description).build();
    }

    @Test
    void 도시정보가_저장된다() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(cityDto);

        mvc.perform(post("/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void 도시정보를_조회한다() throws Exception {
        when(cityService.getCity(1L)).thenReturn(expected);

        mvc.perform(get("/city/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("부산"));
    }

    @Test
    void 도시정보를_업데이트한다() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(cityDto);

        mvc.perform(patch("/city/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(cityService, times(1)).updateCity(longArgumentCaptor.capture(), cityDtoArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
        assertEquals("부산", cityDto.getCityName());
    }

    @Test
    void 도시정보를_삭제한다() throws Exception {
        mvc.perform(delete("/city/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cityService, times(1)).deleteCity(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }

    @Test
    void 사용자별_여행중인_도시리스트를_조회한다() throws Exception {
        when(cityService.getTravelingCitiesByUser(1L)).thenReturn(List.of(expected));

        mvc.perform(get("/city/traveling")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("부산"));

        verify(cityService, times(1)).getTravelingCitiesByUser(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }

    @Test
    void 사용자별_여행예정인_도시리스트를_조회한다() throws Exception {
        when(cityService.getWillTravelCitiesByUser(1L)).thenReturn(List.of(expected));

        mvc.perform(get("/city/willtravel")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("부산"));

        verify(cityService, times(1)).getWillTravelCitiesByUser(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }

    @Test
    void 사용자별_하루동안_등록된_도시리스트를_조회한다() throws Exception {
        when(cityService.getCitiesSaveInOneDayByUser(1L)).thenReturn(List.of(expected));

        mvc.perform(get("/city/save-oneday")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("부산"));

        verify(cityService, times(1)).getCitiesSaveInOneDayByUser(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }

    @Test
    void 사용자별_일주일이내에_한번이상_조회된_도시리스트를_조회한다() throws Exception {
        when(cityService.getCitiesLookAtForOneWeekByUser(1L)).thenReturn(List.of(expected));

        mvc.perform(get("/city/lookat-oneweek")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("부산"));

        verify(cityService, times(1)).getCitiesLookAtForOneWeekByUser(longArgumentCaptor.capture());
        assertEquals(1L, longArgumentCaptor.getValue());
    }
}
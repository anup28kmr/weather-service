package com.ak.web.controller;

import com.ak.bkdprocess.service.WeatherService;
import com.ak.common.dto.ForecastPeriodDTO;
import com.ak.common.dto.PlaceDto;
import com.ak.common.exceptions.WeatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

  private MockMvc mockMvc;

  @Mock
  private WeatherService weatherService;

  @InjectMocks
  private WeatherController weatherController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(weatherController)
        .build();
  }

  @Test
  void testGetWeatherByLocation_Success() throws Exception {
    // Arrange
    String location = "New York";
    List<PlaceDto> placeDtos = Arrays.asList(new PlaceDto("New York", "Sunny", 5, 25));
    when(weatherService.getWeatherByLocation(location)).thenReturn(placeDtos);

    // Act & Assert
    mockMvc.perform(get("/api/weather/location/{location}", location))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name").value("New York"))
        .andExpect(jsonPath("$[0].phenomenon").value("Sunny"));

    verify(weatherService, times(1)).getWeatherByLocation(location);
  }

  @Test
  void testGetWeatherByLocation_NotFound() throws Exception {
    String location = "Unknown";
    when(weatherService.getWeatherByLocation(location)).thenThrow(new WeatherException("Data not found"));

    mockMvc.perform(get("/api/weather/location/{location}", location))
        .andExpect(status().isNotFound());

    verify(weatherService, times(1)).getWeatherByLocation(location);
  }


  @Test
  public void testGetForecastPeriods() throws Exception {

    PlaceDto placeDTO = new PlaceDto("New York", "Rain", 15, 20);
    ForecastPeriodDTO forecastPeriodDTO = new ForecastPeriodDTO("Rain", 15, 20,
        "Light rain in the morning.", List.of(placeDTO));

    List<ForecastPeriodDTO> forecastPeriods = new ArrayList<>();
    forecastPeriods.add(forecastPeriodDTO);

    when(weatherService.getCurrentWeather()).thenReturn(forecastPeriods);

    mockMvc.perform(get("/api/weather")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].phenomenon").value("Rain"))
        .andExpect(jsonPath("$[0].tempMin").value(15))
        .andExpect(jsonPath("$[0].tempMax").value(20))
        .andExpect(jsonPath("$[0].text").value("Light rain in the morning."))
        .andExpect(jsonPath("$[0].places[0].name").value("New York"));
  }
}

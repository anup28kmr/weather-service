package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.bkdprocess.repository.PlaceRepository;
import com.ak.common.dto.ForecastPeriodDTO;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.ForecastPeriod;
import com.ak.common.entity.Place;
import com.ak.common.exceptions.WeatherException;
import com.ak.common.mapper.WeatherMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

  @Mock
  private PlaceRepository placeRepository;

  @Mock
  private ForecastRepository forecastRepository;

  @Mock
  private WeatherMapper weatherMapper;

  @InjectMocks
  private WeatherService weatherService;

  @Test
  public void testGetWeatherByLocation_Success() {
    String location = "London";
    Integer tempMin = 1;
    Integer tempMax = 10;
    String phenomenon = "Cool";
    List<Place> placeList = Collections.singletonList(new Place(1L, phenomenon, location, tempMin, tempMax));
    List<PlaceDto> placeDtoList = Collections.singletonList(new PlaceDto(location, phenomenon, tempMin, tempMax));

    when(placeRepository.findByName(location)).thenReturn(placeList);
    when(weatherMapper.convertPlaceEntityToDto(placeList)).thenReturn(placeDtoList);

    List<PlaceDto> retrievedWeather = weatherService.getWeatherByLocation(location);

    assertThat(retrievedWeather).isNotEmpty();
    assertThat(retrievedWeather.get(0).getName()).isEqualTo(location);
    Mockito.verify(placeRepository).findByName(location);
    Mockito.verify(weatherMapper).convertPlaceEntityToDto(placeList);
  }

  @Test
  public void testGetWeatherByLocation_NotFound() {
    String location = "Unknown City";

    when(placeRepository.findByName(location)).thenReturn(Collections.emptyList());

    try {
      weatherService.getWeatherByLocation(location);
      fail("Expected WeatherException");
    } catch (WeatherException e) {
      assertThat(e.getMessage()).contains("No weather data found for location");
    }

    Mockito.verify(placeRepository).findByName(location);
  }


  @Test
  public void testGetForecastPeriodsForDate() {
    String date = "2024-11-01";

    Place place = new Place();
    place.setId(1L);
    place.setName("New York");
    place.setPhenomenon("Rain");
    place.setTempMin(15);
    place.setTempMax(20);

    ForecastPeriod forecastPeriod = new ForecastPeriod();
    forecastPeriod.setId(1L);
    forecastPeriod.setPhenomenon("Rain");
    forecastPeriod.setTempMin(15);
    forecastPeriod.setTempMax(20);
    forecastPeriod.setText("Light rain in the morning.");
    forecastPeriod.setPlaces(List.of(place));

    List<ForecastPeriod> forecastPeriods = new ArrayList<>();
    forecastPeriods.add(forecastPeriod);

    when(forecastRepository.findByDate(date)).thenReturn(forecastPeriods);

    List<ForecastPeriodDTO> result = weatherService.getCurrentWeather();

    assertEquals(1, result.size());
    assertEquals("Rain", result.get(0).getPhenomenon());
    assertEquals(15, result.get(0).getTempMin());
    assertEquals(20, result.get(0).getTempMax());
    assertEquals("Light rain in the morning.", result.get(0).getText());
    assertEquals(1, result.get(0).getPlaces().size());
    assertEquals("New York", result.get(0).getPlaces().get(0).getName());
  }
}
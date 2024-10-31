package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.bkdprocess.repository.PlaceRepository;
import com.ak.common.dto.ForecastPeriodDTO;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.ForecastPeriod;
import com.ak.common.entity.Place;
import com.ak.common.exceptions.WeatherException;
import com.ak.common.mapper.WeatherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WeatherService {

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private ForecastRepository forecastRepository;

  @Autowired
  private WeatherMapper weatherMapper;

  public List<PlaceDto> getWeatherByLocation(String location) {
    log.debug("Fetching weather data for location: {}", location);

    List<Place> place = placeRepository.findByName(location);
    if (place.isEmpty()) {
      log.warn("No weather data found for location: {}", place);
      throw new WeatherException("No weather data found for location: " + place);
    }

    return weatherMapper.convertPlaceEntityToDto(place);
  }


  public List<ForecastPeriodDTO> getCurrentWeather() {
    log.debug("Fetching current weather data for all locations");

    List<ForecastPeriod> forecastPeriods = forecastRepository.findByDate(LocalDate.now().toString());

    if (forecastPeriods.isEmpty()) {
      log.warn("No weather data found for current day");
    }
    return forecastPeriods.stream()
        .map(weatherMapper::toForecastPeriodDTO)
        .collect(Collectors.toList());
  }

}

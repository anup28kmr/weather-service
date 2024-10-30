package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.bkdprocess.repository.PlaceRepository;
import com.ak.common.dto.ForecastDto;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.Forecast;
import com.ak.common.entity.Place;
import com.ak.common.exceptions.WeatherException;
import com.ak.common.mapper.WeatherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {
  private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

  @Autowired
  private PlaceRepository placeRepository;

  @Autowired
  private ForecastRepository forecastRepository;

  @Autowired
  private WeatherMapper weatherMapper;

  public List<PlaceDto> getWeatherByLocation(String location) {
    logger.debug("Fetching weather data for location: {}", location);

    List<Place> place = placeRepository.findByName(location);
    if (place.isEmpty()) {
      logger.warn("No weather data found for location: {}", place);
      throw new WeatherException("No weather data found for location: " + place);
    }
    return weatherMapper.convertPlaceEntityToDto(place);

  }


  public List<ForecastDto> getCurrentWeather() {
    logger.debug("Fetching current weather data for all locations");

    LocalDate date = LocalDate.now();
    List<Forecast> entities = forecastRepository.findByDate(date.toString());

    if (entities.isEmpty()) {
      logger.warn("No weather data found for current day");
    }

    return weatherMapper.convertForecastToDto(entities);
  }


}

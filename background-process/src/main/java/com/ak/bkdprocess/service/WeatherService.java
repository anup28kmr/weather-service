package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.bkdprocess.repository.PlaceRepository;
import com.ak.common.entity.Forecast;
import com.ak.common.entity.Place;
import com.ak.common.exceptions.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private ForecastRepository forecastRepository;

    public Place getWeatherByLocation(String location) {
        logger.debug("Fetching weather data for location: {}", location);

        Optional<Place> place = placeRepository.findByName(location);
        return place
            .orElseThrow(() -> {
                logger.warn("No weather data found for location: {}", place);
                return new WeatherException("No weather data found for location: " + place);
            });
    }


    public List<Forecast> getCurrentWeather() {
        logger.debug("Fetching current weather data for all locations");
        
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Forecast> entities = forecastRepository.findByDate(LocalDate.now().toString());
        
        if (entities.isEmpty()) {
            logger.warn("No weather data found for current day");
        }
        
        return entities;
    }


}

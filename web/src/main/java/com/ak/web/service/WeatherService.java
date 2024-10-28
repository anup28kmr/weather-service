package com.ak.web.service;

import com.ak.common.dto.WeatherData;
import com.ak.common.entity.Forecast;
import com.ak.common.entity.Location;
import com.ak.common.entity.WeatherEntity;
import com.ak.common.exceptions.WeatherException;
import com.ak.web.repository.LocationRepository;
import com.ak.web.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    private final WeatherRepository weatherRepository;
    private final LocationRepository locationRepository;

    public WeatherService(WeatherRepository weatherRepository, LocationRepository locationRepository) {
        this.weatherRepository = weatherRepository;
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public Location getWeatherByLocation(String location) {
        logger.debug("Fetching weather data for location: {}", location);

        Optional<Location> name = locationRepository.findByName(location);
        return name
            .orElseThrow(() -> {
                logger.warn("No weather data found for location: {}", location);
                return new WeatherException("No weather data found for location: " + location);
            });
    }

//    private Object convertToDto(Location location) {
//
//    }

    @Transactional(readOnly = true)
    public List<Forecast> getCurrentWeather() {
        logger.debug("Fetching current weather data for all locations");
        
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Forecast> entities = weatherRepository.findByForecastDate(LocalDate.now());
        
        if (entities.isEmpty()) {
            logger.warn("No weather data found for current day");
        }
        
        return entities;
    }


}

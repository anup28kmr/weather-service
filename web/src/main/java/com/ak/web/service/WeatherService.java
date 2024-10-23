package com.ak.web.service;

import com.ak.common.dto.WeatherData;
import com.ak.common.entity.WeatherEntity;
import com.ak.common.exceptions.WeatherException;
import com.ak.web.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Transactional(readOnly = true)
    public WeatherData getWeatherByLocation(String location) {
        logger.debug("Fetching weather data for location: {}", location);
        
        return weatherRepository.findFirstByLocationOrderByTimestampDesc(location)
            .map(this::convertToDto)
            .orElseThrow(() -> {
                logger.warn("No weather data found for location: {}", location);
                return new WeatherException("No weather data found for location: " + location);
            });
    }

    @Transactional(readOnly = true)
    public List<WeatherData> getCurrentWeather() {
        logger.debug("Fetching current weather data for all locations");
        
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<WeatherEntity> entities = weatherRepository.findLatestForAllLocations(startOfDay);
        
        if (entities.isEmpty()) {
            logger.warn("No weather data found for current day");
        }
        
        return entities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private WeatherData convertToDto(WeatherEntity entity) {
        return WeatherData.builder()
            .location(entity.getLocation())
            .timestamp(entity.getTimestamp())
            .forecastTime(entity.getForecastTime())
            .temperature(entity.getTemperature())
            .phenomenon(entity.getPhenomenon())
            .humidity(entity.getHumidity())
            .precipitationProbability(entity.getPrecipitationProbability())
            .build();
    }
}

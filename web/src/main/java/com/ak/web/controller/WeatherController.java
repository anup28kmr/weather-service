package com.ak.web.controller;

import com.ak.bkdprocess.service.WeatherService;
import com.ak.common.dto.ForecastDto;
import com.ak.common.dto.PlaceDto;
import com.ak.common.exceptions.WeatherException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Weather forecast API endpoints")
public class WeatherController {
  private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

  private final WeatherService weatherService;

  public WeatherController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @Operation(
      summary = "Get weather forecast by location",
      description = "Retrieves the latest weather forecast for a specific location"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully retrieved weather data",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaceDto.class))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Weather data not found for the specified location",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
      )
  })
  @GetMapping("/location/{location}")
  public ResponseEntity<List<PlaceDto>> getWeatherByLocation(@PathVariable String location) {
    logger.info("Received request for weather data at location: {}", location);
    try {
      List<PlaceDto> weatherData = weatherService.getWeatherByLocation(location);
      return ResponseEntity.ok(weatherData);
    } catch (WeatherException e) {
      logger.warn("Failed to get weather data for location: {}", location, e);
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<ForecastDto>> getCurrentWeather() {
    logger.info("Received request for current weather data");
    List<ForecastDto> weatherData = weatherService.getCurrentWeather();
    return ResponseEntity.ok(weatherData);
  }

  @ExceptionHandler(WeatherException.class)
  public ResponseEntity<String> handleWeatherException(WeatherException e) {
    logger.error("Weather service error", e);
    return ResponseEntity.internalServerError().body(e.getMessage());
  }
}
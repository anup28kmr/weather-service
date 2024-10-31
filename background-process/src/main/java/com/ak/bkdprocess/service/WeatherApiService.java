package com.ak.bkdprocess.service;

import com.ak.bkdprocess.config.HttpClientWithRetry;
import com.ak.common.exceptions.WeatherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class WeatherApiService {

  private final HttpClient httpClient = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(10))
      .build();
  HttpClientWithRetry clientWithRetry = new HttpClientWithRetry(httpClient, 3, Duration.ofSeconds(2));
  @Value("${weather-api:https://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng}")
  private String WEATHER_API_URL;

  public String fetchWeatherXMLData() {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(WEATHER_API_URL))
        .GET()
        .build();

    try {
      HttpResponse<String> response = clientWithRetry.sendWithRetry(request);

      if (response.statusCode() == 200) {
        return response.body();
      } else {
        throw new WeatherException("Failed to fetch weather data. Status code: "
            + response.statusCode());
      }
    } catch (Exception e) {
      throw new WeatherException("Error fetching weather data: " + e.getMessage(), e);
    }
  }
}
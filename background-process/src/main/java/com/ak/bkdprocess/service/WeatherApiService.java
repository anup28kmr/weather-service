package com.ak.bkdprocess.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class WeatherApiService {
    private final HttpClient httpClient;
    @Value("${weather-api}")
    private String WEATHER_API_URL;

    public WeatherApiService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String fetchWeatherXMLData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(WEATHER_API_URL))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Failed to fetch weather data. Status code: "
                        + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data: " + e.getMessage(), e);
        }
    }
}
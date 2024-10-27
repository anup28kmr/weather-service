package com.ak.bkdprocess.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherApiService {
    private final WebClient webClient;
    private static final String API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng";

    public WeatherApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    public Mono<String> fetchWeatherData() {
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
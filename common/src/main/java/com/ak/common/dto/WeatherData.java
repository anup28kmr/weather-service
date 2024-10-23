package com.ak.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {
    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime forecastTime;

    private Double temperature;
    private String phenomenon;
    private Integer humidity;
    private Integer precipitationProbability;
}
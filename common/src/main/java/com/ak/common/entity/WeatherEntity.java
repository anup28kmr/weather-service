package com.ak.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private LocalDateTime forecastTime;

    private Double temperature;

    private String phenomenon;

    private Integer humidity;

    @Column(name = "precipitation_probability")
    private Integer precipitationProbability;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
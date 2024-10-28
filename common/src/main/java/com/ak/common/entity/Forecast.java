package com.ak.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FORECAST")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "periods")
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forecast_id")
    private Long id;

    @Column(name = "forecast_date", nullable = false, unique = true)
    private LocalDate forecastDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "forecast", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DayPeriod> periods = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
package com.ak.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOCATION_FORECAST")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"dayPeriod", "location"})
public class LocationForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_forecast_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id", nullable = false)
    private DayPeriod dayPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "phenomenon")
    private String phenomenon;

    @Column(name = "temp_min", precision = 4, scale = 1)
    private BigDecimal tempMin;

    @Column(name = "temp_max", precision = 4, scale = 1)
    private BigDecimal tempMax;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
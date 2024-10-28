package com.ak.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DAY_PERIOD")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"forecast", "locationForecasts"})
public class DayPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forecast_id", nullable = false)
    private Forecast forecast;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false)
    private PeriodType periodType;

    @Column(name = "phenomenon")
    private String phenomenon;

    @Column(name = "temp_min", precision = 4, scale = 1)
    private BigDecimal tempMin;

    @Column(name = "temp_max", precision = 4, scale = 1)
    private BigDecimal tempMax;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "dayPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocationForecast> locationForecasts = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}



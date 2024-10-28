package com.ak.web.repository;

import com.ak.common.entity.Forecast;
import com.ak.common.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findByForecastDate(LocalDate now);
//    Optional<WeatherEntity> findFirstByLocationOrderByTimestampDesc(String location);
//
//    @Query("SELECT w FROM WeatherEntity w WHERE w.timestamp >= :startOfDay " +
//           "AND w.timestamp = (SELECT MAX(w2.timestamp) FROM WeatherEntity w2 WHERE w2.location = w.location)")
//    List<WeatherEntity> findLatestForAllLocations(@Param("startOfDay") LocalDateTime startOfDay);
}
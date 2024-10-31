package com.ak.bkdprocess.repository;

import com.ak.common.entity.Forecast;
import com.ak.common.entity.ForecastPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

  @Query("SELECT f.forecastPeriod FROM Forecast f WHERE f.date = ?1")
  List<ForecastPeriod> findByDate(String string);
}

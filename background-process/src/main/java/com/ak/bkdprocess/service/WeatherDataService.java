package com.ak.bkdprocess.service;

import com.ak.bkdprocess.repository.ForecastRepository;
import com.ak.common.dto.DayDto;
import com.ak.common.dto.ForecastsDto;
import com.ak.common.dto.NightDto;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.Forecast;
import com.ak.common.entity.ForecastPeriod;
import com.ak.common.entity.PeriodType;
import com.ak.common.entity.Place;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class WeatherDataService {

  private final ForecastRepository forecastRepository;

  private final WeatherApiService weatherApiService;

  @Scheduled(fixedRateString = "${app.scheduled.interval}")
  void updateWeatherData() {
    log.info("Starting scheduled weather data update");
    processWeatherData(weatherApiService.fetchWeatherXMLData());
  }

  void processWeatherData(String xmlString) {
    try {
      JAXBContext jaxbContext;
      try {
        jaxbContext = JAXBContext.newInstance(ForecastsDto.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ForecastsDto forecastsDto = (ForecastsDto) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
        saveData(forecastsDto);
      } catch (JAXBException e) {
        log.error("Error in parsing the XML data", e);
      }
    } catch (Exception e) {
      log.error("Error processing weather data", e);
    }
  }

  private void saveData(ForecastsDto forecastsDto) {
    List<Forecast> forecastList = forecastsDto.getForecast().stream()
        .filter(Objects::nonNull)
        .map(forecastDto -> {
          Forecast forecast = new Forecast();
          forecast.setDate(forecastDto.getDate());
          forecast.setForecastPeriod(createForecastPeriod(forecastDto.getDay(), forecastDto.getNight()));
          return forecast;
        })
        .collect(Collectors.toList());

    forecastRepository.saveAll(forecastList);
  }

  private List<ForecastPeriod> createForecastPeriod(DayDto day, NightDto night) {
    List<ForecastPeriod> forecastPeriodList = new ArrayList<>();

    if (day != null) {
      forecastPeriodList.add(createPeriodFromDay(day));
    }
    if (night != null) {
      forecastPeriodList.add(createPeriodFromNight(night));
    }
    return forecastPeriodList;
  }

  private ForecastPeriod createPeriodFromDay(DayDto day) {
    ForecastPeriod dayPeriod = new ForecastPeriod();
    dayPeriod.setPeriod(PeriodType.DAY);
    dayPeriod.setPhenomenon(day.getPhenomenon());
    dayPeriod.setTempMin(day.getTempMin());
    dayPeriod.setTempMax(day.getTempMax());
    dayPeriod.setPlaces(setPlaces(day.getPlaces()));
    return dayPeriod;
  }

  private ForecastPeriod createPeriodFromNight(NightDto night) {
    ForecastPeriod nightPeriod = new ForecastPeriod();
    nightPeriod.setPeriod(PeriodType.NIGHT);
    nightPeriod.setPhenomenon(night.getPhenomenon());
    nightPeriod.setTempMin(night.getTempMin());
    nightPeriod.setTempMax(night.getTempMax());
    nightPeriod.setPlaces(setPlaces(night.getPlaces()));
    return nightPeriod;
  }

  List<Place> setPlaces(List<PlaceDto> places) {
    if (places == null) {
      return Collections.emptyList();
    }
    return places.stream()
        .filter(Objects::nonNull)
        .map(placeDto -> {
          Place place = new Place();
          place.setName(placeDto.getName());
          place.setPhenomenon(placeDto.getPhenomenon());
          place.setTempMin(placeDto.getTempMin());
          place.setTempMax(placeDto.getTempMax());
          return place;
        })
        .collect(Collectors.toList());
  }

}
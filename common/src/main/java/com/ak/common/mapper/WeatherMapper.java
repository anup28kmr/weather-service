package com.ak.common.mapper;

import com.ak.common.dto.DayDto;
import com.ak.common.dto.ForecastDto;
import com.ak.common.dto.NightDto;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.Day;
import com.ak.common.entity.Forecast;
import com.ak.common.entity.Night;
import com.ak.common.entity.Place;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherMapper {

  public List<PlaceDto> convertPlaceEntityToDto(List<Place> placeEntity) {
    List<PlaceDto> placeDtos = new ArrayList<>();
    for (Place place : placeEntity) {
      PlaceDto placeDtoDto = new PlaceDto();
      placeDtoDto.setName(place.getName());
      placeDtoDto.setPhenomenon(place.getPhenomenon());
      placeDtoDto.setTempMax(place.getTempMax());
      placeDtoDto.setTempMin(place.getTempMin());
      placeDtos.add(placeDtoDto);
    }
    return placeDtos;
  }

  public List<ForecastDto> convertForecastToDto(List<Forecast> entities) {
    List<ForecastDto> forecastDtos = new ArrayList<>();
    for (Forecast forecast : entities) {
      ForecastDto forecastDto = new ForecastDto();
      forecastDto.setDate(forecast.getDate());
      forecastDto.setNight(setNightData(forecast.getNight()));
      forecastDto.setDay(setDayData(forecast.getDay()));
      forecastDtos.add(forecastDto);
    }
    return forecastDtos;
  }

  private DayDto setDayData(Day day) {
    DayDto dayDto = new DayDto();
    dayDto.setPhenomenon(day.getPhenomenon());
    dayDto.setTempmax(day.getTempMax());
    dayDto.setTempmin(day.getTempMin());
    return dayDto;
  }

  private NightDto setNightData(Night night) {
    NightDto nightDto = new NightDto();
    nightDto.setPhenomenon(night.getPhenomenon());
    nightDto.setTempMax(night.getTempMax());
    nightDto.setTempMin(night.getTempMin());
    return nightDto;
  }
}

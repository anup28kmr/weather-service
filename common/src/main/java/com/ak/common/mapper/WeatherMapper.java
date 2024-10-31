package com.ak.common.mapper;

import com.ak.common.dto.ForecastPeriodDTO;
import com.ak.common.dto.PlaceDto;
import com.ak.common.entity.ForecastPeriod;
import com.ak.common.entity.Place;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  public ForecastPeriodDTO toForecastPeriodDTO(ForecastPeriod period) {
    List<PlaceDto> placeDTOs = period.getPlaces().stream()
        .map(this::toPlaceDTO)
        .collect(Collectors.toList());

    return new ForecastPeriodDTO(period.getPhenomenon(), period.getTempMin(),
        period.getTempMax(), period.getText(), placeDTOs);
  }

  public PlaceDto toPlaceDTO(Place place) {
    return new PlaceDto(place.getName(), place.getPhenomenon(),
        place.getTempMin(), place.getTempMax());
  }
}

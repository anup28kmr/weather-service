package com.ak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DayDto {
  public String phenomenon;
  public Integer tempmin;
  public Integer tempmax;
  public String text;
  public List<PlaceDto> placeDto;
  public String peipsi;

  public String getPhenomenon() {
    return phenomenon;
  }

  public void setPhenomenon(String phenomenon) {
    this.phenomenon = phenomenon;
  }

  public Integer getTempmin() {
    return tempmin;
  }

  public void setTempmin(Integer tempmin) {
    this.tempmin = tempmin;
  }

  public Integer getTempmax() {
    return tempmax;
  }

  public void setTempmax(Integer tempmax) {
    this.tempmax = tempmax;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<PlaceDto> getPlaceDto() {
    return placeDto;
  }

  public void setPlaceDto(List<PlaceDto> placeDto) {
    this.placeDto = placeDto;
  }

  public String getPeipsi() {
    return peipsi;
  }

  public void setPeipsi(String peipsi) {
    this.peipsi = peipsi;
  }
}
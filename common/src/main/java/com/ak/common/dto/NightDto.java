package com.ak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NightDto {
  public String phenomenon;
  public Integer tempMin;
  public Integer tempMax;
  public String text;
  public List<PlaceDto> places;
  public String sea;
  public String peipsi;

  public String getPhenomenon() {
    return phenomenon;
  }

  public void setPhenomenon(String phenomenon) {
    this.phenomenon = phenomenon;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getSea() {
    return sea;
  }

  public void setSea(String sea) {
    this.sea = sea;
  }

  public String getPeipsi() {
    return peipsi;
  }

  public void setPeipsi(String peipsi) {
    this.peipsi = peipsi;
  }

  public Integer getTempMin() {
    return tempMin;
  }

  public void setTempMin(Integer tempMin) {
    this.tempMin = tempMin;
  }

  public Integer getTempMax() {
    return tempMax;
  }

  public void setTempMax(Integer tempMax) {
    this.tempMax = tempMax;
  }

  public List<PlaceDto> getPlaces() {
    return places;
  }

  public void setPlaces(List<PlaceDto> places) {
    this.places = places;
  }
}

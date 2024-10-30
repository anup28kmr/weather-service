package com.ak.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaceDto {
  public String name;
  public String phenomenon;
  public Integer tempMin;
  public Integer tempMax;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhenomenon() {
    return phenomenon;
  }

  public void setPhenomenon(String phenomenon) {
    this.phenomenon = phenomenon;
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
}
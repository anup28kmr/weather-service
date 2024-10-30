package com.ak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForecastDto {
  @JsonProperty("night")
  public NightDto night;
  @JsonProperty("day")
  public DayDto day;
  private String date;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public NightDto getNight() {
    return night;
  }

  public void setNight(NightDto night) {
    this.night = night;
  }

  public DayDto getDay() {
    return day;
  }

  public void setDay(DayDto day) {
    this.day = day;
  }
}
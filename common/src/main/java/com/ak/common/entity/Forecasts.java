package com.ak.common.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Forecasts {
  public List<Forecast> forecast;

  public List<Forecast> getForecast() {
    return forecast;
  }
}
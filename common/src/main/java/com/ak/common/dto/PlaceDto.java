package com.ak.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "place")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaceDto {
  public String name;
  public String phenomenon;
  @XmlElement(name = "tempmin")
  public Integer tempMin;
  @XmlElement(name = "tempmax")
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
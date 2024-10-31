package com.ak.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "day")
@XmlAccessorType(XmlAccessType.FIELD)
public class DayDto {
  public String phenomenon;
  @XmlElement(name = "tempmin")
  public Integer tempMin;
  @XmlElement(name = "tempmax")
  public Integer tempMax;
  public String text;
  @XmlElement(name = "place")
  public List<PlaceDto> places;
  public String peipsi;

}
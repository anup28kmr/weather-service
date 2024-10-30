package com.ak.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;


@Entity
@Table(name = "place")
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @XmlElement(name = "phenomenon")
  private String phenomenon;

  @XmlElement(name = "tempmin")
  private Integer tempMin;

  @XmlElement(name = "tempmax")
  private Integer tempMax;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

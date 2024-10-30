package com.ak.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

@Entity
@Table(name = "night")
@XmlAccessorType(XmlAccessType.FIELD)
public class Night {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @XmlElement(name = "phenomenon")
  private String phenomenon;

  @XmlElement(name = "tempmin")
  private Integer tempMin;

  @XmlElement(name = "tempmax")
  private Integer tempMax;

  @Column(length = 1024) // Assuming text can be long
  private String text;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "night_id")
  @XmlElement(name = "place")
  private List<Place> places;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<Place> getPlaces() {
    return places;
  }

  public void setPlaces(List<Place> places) {
    this.places = places;
  }
}

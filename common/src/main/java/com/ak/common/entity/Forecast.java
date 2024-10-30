package com.ak.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "forecast")
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class Forecast {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @XmlAttribute(name = "date")
  private String date;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "night_id")
  @XmlElement(name = "night")
  private Night night;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "day_id")
  @XmlElement(name = "day")
  private Day day;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Night getNight() {
    return night;
  }

  public void setNight(Night night) {
    this.night = night;
  }

  public Day getDay() {
    return day;
  }

  public void setDay(Day day) {
    this.day = day;
  }
}

package com.ak.common.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "forecast")

public class Forecast {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @XmlAttribute(name = "date")
  private String date;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "forecast_id")
  private List<ForecastPeriod> forecastPeriod;

  public Forecast(long l, String date) {
    this.date = date;
    this.id = l;
  }
}

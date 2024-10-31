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
import jakarta.xml.bind.annotation.XmlElement;
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
@Table(name = "forecast_period")
public class ForecastPeriod {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String phenomenon;

  private Integer tempMin;

  private Integer tempMax;

  @Column(length = 1024) // Assuming text can be long
  private String text;

  @Column
  private PeriodType period;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "forecast_period_id")
  @XmlElement(name = "place")
  private List<Place> places;
}

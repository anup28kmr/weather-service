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
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "day")
@XmlAccessorType(XmlAccessType.FIELD)
public class Day {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String phenomenon;

  @XmlElement(name = "tempmin")
  private Integer tempMin;

  @XmlElement(name = "tempmax")
  private Integer tempMax;

  @Column(length = 1024) // Assuming text can be long
  private String text;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "day_id")
  @XmlElement(name = "place")
  private List<Place> places;

}

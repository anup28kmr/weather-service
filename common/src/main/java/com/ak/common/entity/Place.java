package com.ak.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
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

}

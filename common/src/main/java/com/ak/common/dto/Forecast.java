package com.ak.common.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class Forecast {
	@XmlAttribute
	private String date;
	public Night night;
	public Day day;
	public String text;
}
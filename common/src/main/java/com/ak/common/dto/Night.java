package com.ak.common.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "night")
@XmlAccessorType(XmlAccessType.FIELD)
public class Night {
	public String phenomenon;
	public int tempmin;
	public int tempmax;
	public String text;
	public List<Place> place;
	public String sea;
	public String peipsi;
}

package com.ak.common.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "place")
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {
	public String name;
	public String phenomenon;
	public int tempmin;
	public int tempmax;
}
package com.hashedin.artcollective.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SizeBucket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String title;
	private Double lowerValue;
	private Double upperValue;
	
	public SizeBucket(Long id, String title, Double lowerValue,
			Double upperValue) {
		this.id = id;
		this.title = title;
		this.lowerValue = lowerValue;
		this.upperValue = upperValue;
	}
	public SizeBucket() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getLowerValue() {
		return lowerValue;
	}
	public void setLowerValue(Double lowerValue) {
		this.lowerValue = lowerValue;
	}
	public Double getUpperValue() {
		return upperValue;
	}
	public void setUpperValue(Double upperValue) {
		this.upperValue = upperValue;
	}
	

}

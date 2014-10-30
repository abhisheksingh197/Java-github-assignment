package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PriceBucket {
	
	@Id
	private Long id;
	private String title;
	private Double lowerRange;
	private Double upperRange;
	public PriceBucket(Long id, String pTitle, Double lower,
			Double upper) {
		super();
		this.id = id;
		this.title = pTitle;
		this.lowerRange = lower;
		this.upperRange = upper;
	}
	public PriceBucket() {
		// TODO Auto-generated constructor stub
	}
	public Double getLowerRange() {
		return lowerRange;
	}
	public void setLowerRange(Double lowerRange) {
		this.lowerRange = lowerRange;
	}
	public Double getUpperRange() {
		return upperRange;
	}
	public void setUpperRange(Double upperRange) {
		this.upperRange = upperRange;
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
}

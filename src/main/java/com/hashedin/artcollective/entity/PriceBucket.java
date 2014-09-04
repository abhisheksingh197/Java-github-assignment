package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PriceBucket {
	
	@Id
	private Long id;
	private String title;
	private double lowerRange;
	private double upperRange;
	public PriceBucket(Long pId, String pTitle, double lower,
			double upper) {
		super();
		this.id = pId;
		this.title = pTitle;
		this.lowerRange = lower;
		this.upperRange = upper;
		
	}
	public PriceBucket() {
		// TODO Auto-generated constructor stub
	}
	public double getLowerRange() {
		return lowerRange;
	}
	public void setLowerRange(double lowerRange) {
		this.lowerRange = lowerRange;
	}
	public double getUpperRange() {
		return upperRange;
	}
	public void setUpperRange(double upperRange) {
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

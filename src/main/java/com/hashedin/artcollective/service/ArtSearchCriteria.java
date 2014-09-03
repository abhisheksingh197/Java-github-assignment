package com.hashedin.artcollective.service;

public class ArtSearchCriteria {

	private String color1;
	private String color2;
	private int color1Weight;
	private int color2Weight;
	public int getColor1Weight() {
		return color1Weight;
	}

	public void setColor1Weight(int color1Weight) {
		this.color1Weight = color1Weight;
	}

	public int getColor2Weight() {
		return color2Weight;
	}

	public void setColor2Weight(int color2Weight) {
		this.color2Weight = color2Weight;
	}

	public String getColour1() {
		return color1;
	}

	public void setColour1(String colour1) {
		this.color1 = colour1;
	}

	public String getColour2() {
		return color2;
	}

	public void setColour2(String colour2) {
		this.color2 = colour2;
	}
	
	
	public boolean hasSearchByColour() {
		return color1 != null;
	}

	
}

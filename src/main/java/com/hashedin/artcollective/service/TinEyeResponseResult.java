package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonSetter;


public class TinEyeResponseResult {
	private String score;
	private String filepath;
	private TinEyeMetadata metadata;
	private String color;
	private String name;
	private String rank;
	private String weight;
	private String metafieldClass;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getMetafieldClass() {
		return metafieldClass;
	}
	@JsonSetter("class")
	public void setMetafieldClass(String metafieldClass) {
		this.metafieldClass = metafieldClass;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public TinEyeMetadata getMetadata() {
		return metadata;
	}
	public void setMetadata(TinEyeMetadata metadata) {
		this.metadata = metadata;
	}
	
}

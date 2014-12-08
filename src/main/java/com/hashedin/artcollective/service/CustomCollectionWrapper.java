package com.hashedin.artcollective.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;


public class CustomCollectionWrapper {
	
	private CustomCollection customCollection;
	
	private List<CustomCollection> customCollections;
	
	private List<Collect> collectCollections;

	public CustomCollection getCustomCollection() {
		return customCollection;
	}
	@JsonSetter("custom_collection")
	public void setCustomCollection(CustomCollection customCollection) {
		this.customCollection = customCollection;
	}
	public List<CustomCollection> getCustomCollections() {
		return customCollections;
	}
	@JsonSetter("custom_collections")
	public void setCustomCollections(List<CustomCollection> customCollections) {
		this.customCollections = customCollections;
	}
	public List<Collect> getCollectCollections() {
		return collectCollections;
	}
	@JsonSetter("collects")
	public void setCollectCollections(List<Collect> collectCollections) {
		this.collectCollections = collectCollections;
	}
	
}

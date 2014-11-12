package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonSetter;


public class CustomCollectionWrapper {
	
	private CustomCollection customCollection;

	public CustomCollection getCustomCollection() {
		return customCollection;
	}
	@JsonSetter("custom_collection")
	public void setCustomCollection(CustomCollection customCollection) {
		this.customCollection = customCollection;
	}
	
}

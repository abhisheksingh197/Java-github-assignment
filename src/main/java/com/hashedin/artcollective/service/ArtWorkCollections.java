package com.hashedin.artcollective.service;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

class ArtWorkCollections {
	
	@JsonProperty("custom_collections")
	private List<Collection> customCollections;

	public List<Collection> getCustomCollections() {
		return customCollections;
	}

	public void setCustomCollections(List<Collection> collections) {
		this.customCollections = collections;
	}
	
}

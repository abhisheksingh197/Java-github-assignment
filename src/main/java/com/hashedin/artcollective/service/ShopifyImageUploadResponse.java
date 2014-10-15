package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.hashedin.artcollective.entity.Image;

public class ShopifyImageUploadResponse {
	private Image image;

	public Image getImage() {
		return image;
	}
	
	@JsonSetter("image")
	public void setImage(Image image) {
		this.image = image;
	} 

}

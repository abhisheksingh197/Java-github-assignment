package com.hashedin.artcollective.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {

	@Id
	private long id;
	//private DateTime createdAt;
	//private DateTime updatedAt;
	private long productId;
	private String imgSrc;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	
	@JsonSetter("src")
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

}

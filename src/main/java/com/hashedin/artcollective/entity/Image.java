package com.hashedin.artcollective.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	//private DateTime createdAt;
	//private DateTime updatedAt;
	private long productId;
	private String imgSrc;
	private int height;
	private int width;
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
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

package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Options {
	private String id;
	private String name;
	private Long position;
	private Long productId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public Long getProductId() {
		return productId;
	}
	
	@JsonSetter("product_id")
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}

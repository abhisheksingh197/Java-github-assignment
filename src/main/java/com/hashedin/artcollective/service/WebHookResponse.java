package com.hashedin.artcollective.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebHookResponse {
	
	private Long id;
	private String productType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}
	@JsonSetter("product_type")
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}

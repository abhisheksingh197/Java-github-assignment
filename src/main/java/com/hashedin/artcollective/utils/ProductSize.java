package com.hashedin.artcollective.utils;

import com.hashedin.artcollective.entity.ArtworkVariant;

public class ProductSize {
	
	private Double productWidth;
	private Double productHeight;
	
	public ProductSize(ArtworkVariant artworkVariant) {
		this.productHeight = Double.valueOf(artworkVariant.getOption1().split("[Xx]")[0].split("\"")[0]);
		this.productWidth = Double.valueOf(artworkVariant.getOption1().split("[Xx]")[1].split("\"")[0]);
	}
	
	public Double getProductLength() {
		return productWidth;
	}
	public void setProductWidth(Double productWidth) {
		this.productWidth = productWidth;
	}
	public Double getProductBreadth() {
		return productHeight;
	}
	public void setProductHeight(Double productHeight) {
		this.productHeight = productHeight;
	}
}

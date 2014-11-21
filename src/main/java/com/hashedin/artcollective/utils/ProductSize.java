package com.hashedin.artcollective.utils;

import com.hashedin.artcollective.entity.ArtworkVariant;

public class ProductSize {
	
	private Double productLength;
	private Double productBreadth;
	
	public ProductSize(ArtworkVariant artworkVariant) {
		this.productLength = Double.valueOf(artworkVariant.getOption1().split("[Xx]")[0].split("\"")[0]);
		this.productBreadth = Double.valueOf(artworkVariant.getOption1().split("[Xx]")[1].split("\"")[0]);
	}
	
	public Double getProductLength() {
		return productLength;
	}
	public void setProductLength(Double productLength) {
		this.productLength = productLength;
	}
	public Double getProductBreadth() {
		return productBreadth;
	}
	public void setProductBreadth(Double productBreadth) {
		this.productBreadth = productBreadth;
	}
}

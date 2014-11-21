package com.hashedin.artcollective.service;

/**
 * Wrapper Class for a single product that is returned by Shopify on creating
 * a dynamic product for Artwork add on's. 
 * @author hasher
 *
 */
public class DynamicProduct {
	
	private CustomCollection product;

	public CustomCollection getProduct() {
		return product;
	}

	public void setProduct(CustomCollection product) {
		this.product = product;
	}
	
}

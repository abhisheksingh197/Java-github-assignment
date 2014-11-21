package com.hashedin.artcollective.service;
import java.util.List;

/**
 * Wrapper Class for products(CustomCollection) that is returned by Shopify
 * @author hasher
 *
 */
class ShopifyProducts {

	
	private List<CustomCollection> products;

	public List<CustomCollection> getProducts() {
		return products;
	}

	public void setProducts(List<CustomCollection> products) {
		this.products = products;
	}

}

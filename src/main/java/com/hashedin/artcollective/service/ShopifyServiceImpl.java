package com.hashedin.artcollective.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ShopifyServiceImpl implements ShopifyService {

	private final String baseUri;
	private final RestOperations rest;
	
	@Autowired
	public ShopifyServiceImpl(RestOperations rest, 
								@Value("${shopify.baseurl}") String baseUrl) {
		this.rest = rest;
		this.baseUri = baseUrl;
	}

	@Override
	public List<Product> getArtWorkProductsSinceLastModified(DateTime lastModified) {
		ShopifyProducts products = rest.getForObject(
				baseUri + "products.json?product_type=artworks", ShopifyProducts.class);
		return products.getProducts();
	}

	@Override
	public List<Collection> getCollectionsForProduct(long productId) {
		ArtWorkCollections collections = rest.getForObject(
				baseUri + "custom_collections.json?product_id=" + productId, ArtWorkCollections.class);
		return collections.getCustomCollections();
	}
	
	@Override
	public List<MetaField> getMetaFieldsForProduct(long productId) {
		ArtWorkMetafields metafields = rest.getForObject(
				baseUri + "products/" + productId + "/metafields.json", ArtWorkMetafields.class);
		return metafields.getMetafields();
	}

	@Override
	public List<Product> getFrameProductsSinceLastModified(DateTime lastRunTime) {
		ShopifyProducts products = rest.getForObject(
				baseUri + "products.json?product_type=frames", ShopifyProducts.class);
		return products.getProducts();
	}

}

package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ShopifyServiceImpl implements ShopifyService {

	private static final int MAX_PAGE_SIZE = 100;
	
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
		String queryString = "?product_type=artworks";
		
		int count = rest.getForObject(baseUri + "products/count.json" + queryString,
				ShopifyProductsCount.class).getCount();
		List<Product> productsList = new ArrayList<>();
		
		int numPages = (count / MAX_PAGE_SIZE) + 1;
		for (int page = 1; page <= numPages; page++) {
			ShopifyProducts products = rest.getForObject(
					String.format("%s%s%s&limit=%d&page=%d", baseUri, 
							"products.json", queryString, MAX_PAGE_SIZE, page), 
					ShopifyProducts.class);
			productsList.addAll(products.getProducts());
		}
		
		return productsList;
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

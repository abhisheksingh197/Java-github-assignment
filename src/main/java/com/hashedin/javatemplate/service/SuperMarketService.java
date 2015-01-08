package com.hashedin.javatemplate.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hashedin.javatemplate.entity.SuperMarket;
import com.hashedin.javatemplate.repository.SuperMarketRepository;

@Service
public class SuperMarketService {
	
	@Autowired
	private SuperMarketRepository superMarketRepository;

	@Autowired
	private RestTemplate rest;
	
	public Long getTotalCount() {
		return superMarketRepository.count();
	}
	
	public List<SuperMarket> fetchAllSuperMarkets() {
		return (List<SuperMarket>) superMarketRepository.findAll();
	}

	public SuperMarket createNewSuperMarket(SuperMarket superMarketObject) {
		return superMarketRepository.save(superMarketObject);
	}
	
	public List<CustomCollection> getAllProductsModifiedSince(DateTime lastModified) {
		String queryString = "?product_type=artworks";
		if (lastModified != null) {
			queryString = "?product_type=artworks&updated_at_min=".concat(lastModified.toString());
		}
		
		List<CustomCollection> productsList = new ArrayList<CustomCollection>();
		ShopifyProducts products = rest.getForObject(
					String.format("%s%s%s&limit=%d&page=%d", "https://artcollectiveindialtd.myshopify.com/admin/", 
							"products.json", queryString, 100, 1), 
					ShopifyProducts.class);
		productsList.addAll(products.getProducts());
		return productsList;
	}
}
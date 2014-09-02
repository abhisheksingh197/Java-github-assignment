package com.hashedin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ProductServiceImpl implements ProductService {

	private final String baseUri;
	private final RestOperations rest;
	
	@Autowired
	public ProductServiceImpl(RestOperations rest, 
								@Value("${productservice.baseurl}") String baseUrl) {
		
		this.rest = rest;
		this.baseUri = baseUrl;
	}


	@Override
	public List<PersonData> getProductsSinceLastModified(Date lastModified) {
		PersonDataList products = rest.getForObject(baseUri + "products.json", PersonDataList.class);
		return products.getProducts();
	}

}

package com.hashedin.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import com.hashedin.BaseUnitTest;
import com.hashedin.service.PersonData;
import com.hashedin.service.ProductService;

public class ProductServiceTest extends BaseUnitTest {
	
	@Value("${productservice.baseurl}")
	private String baseUrl;
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private RestTemplate rest;
	
	@Test
	public void testFetchProducts() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer.expect(requestTo(baseUrl + "products.json"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("single_product.json"));
		
		List<PersonData> products = service.getProductsSinceLastModified(null);
		assertEquals(products.size(), 1);
		
		PersonData product = products.get(0);
		assertEquals(product.getId(), 331204149);
		mockShopifyServer.verify();
	}
	
}

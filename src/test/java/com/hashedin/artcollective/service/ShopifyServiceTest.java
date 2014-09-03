package com.hashedin.artcollective.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import com.hashedin.artcollective.BaseUnitTest;

public class ShopifyServiceTest extends BaseUnitTest {
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Autowired
	private ShopifyService service;
	
	@Autowired
	private RestTemplate rest;
	
	@Test
	public void testFetchProducts() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer.expect(requestTo(shopifyBaseUrl + "products.json"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("single_product.json"));
		
		List<Product> products = service.getProductsSinceLastModified(null);
		assertEquals(products.size(), 1);
		
		Product product = products.get(0);
		assertEquals(product.getId(), 331204149);
		assertNotNull(product.getCreatedAt());
		assertNotNull(product.getUpdatedAt());
		assertNotNull(product.getPublishedAt());
		
		mockShopifyServer.verify();
	}
	
}

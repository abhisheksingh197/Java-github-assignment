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
		mockShopifyServer.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks"))
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
	
	@Test
	public void testFetchCollections() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=343096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_343096747.json"));
		
		List<Collection> collections = service.getCollectionsForProduct(343096747L);
		assertEquals(collections.size(), 4);
		Collection collection = collections.get(0);
		assertEquals(collection.getTitle(), "subject_geometric");
		mockShopifyServer.verify();
	}
	
	@Test
	public void testFetchMetafields() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer
				.expect(requestTo(shopifyBaseUrl
						+ "products/343096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_343096747.json"));
		
		List<MetaField> metafields = service.getMetaFieldsForProduct(343096747L);
		assertEquals(metafields.size(), 2);
		MetaField metafield = metafields.get(0);
		assertEquals(metafield.getKey(), "is_canvas_available");
		mockShopifyServer.verify();
	}
	
	
}
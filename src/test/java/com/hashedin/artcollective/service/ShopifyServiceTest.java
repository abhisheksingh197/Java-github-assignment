package com.hashedin.artcollective.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


import com.hashedin.artcollective.BaseUnitTest;

public class ShopifyServiceTest extends BaseUnitTest {
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Autowired
	private ShopifyService service;
	
	@Autowired
	private RestTemplate rest;
	
	@Test
	public void testFetchArtworks() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		
		mockShopifyServer.expect(requestTo(shopifyBaseUrl + "products/count.json?product_type=artworks"))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyArtworksCount(1));
		
		mockShopifyServer.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks&limit=100&page=1"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("single_product.json"));
		
		List<CustomCollection> products = service.getArtWorkProductsSinceLastModified(null);
		assertEquals(products.size(), 1);
		
		CustomCollection product = products.get(0);
		assertEquals(product.getId(), 331204149);
		assertNotNull(product.getCreatedAt());
		assertNotNull(product.getUpdatedAt());
		assertNotNull(product.getPublishedAt());
		
		mockShopifyServer.verify();
	}
	
	@Test
	public void testFetchFrames() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("frames.json"));
		
		List<CustomCollection> products = service.getAddOnProductsSinceLastModified(null, "frames");
		assertEquals(products.size(), 6);
		
		CustomCollection product = products.get(0);
		assertEquals(product.getId(), 343096747);
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
		
		List<MetaField> metafields = service.getMetaFields("products", 343096747L);
		assertEquals(metafields.size(), 2);
		MetaField metafield = metafields.get(0);
		assertEquals(metafield.getKey(), "is_canvas_available");
		mockShopifyServer.verify();
	}
	
	@Test
	public void testPostImageColorsMetaField() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);
		mockShopifyServer
				.expect(requestTo(shopifyBaseUrl
						+ "products/343096747/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("color_extract_metafield.json"));
		
		long productId = 343096747;
		String imageColors = "11d45f,edc9af";
		service.postImageColorsMetaField(productId, imageColors);
	}
	
	@Test
	public void testAddProductToFavoriteCollection() {
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json?title=customer_1234567_favorites"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("get_customer_collection.json"));
				
		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections/12345678.json"))
			.andExpect(method(HttpMethod.PUT))
			.andRespond(withJson("get_customer_collection.json"));

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json?title=customer_1234567_favorites"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("get_empty_customer_collection.json"));

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("get_customer_collection.json"));

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json?title=customer_1234567_favorites"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("get_customer_collection.json"));

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "collects/987654-12345678.json"))
			.andExpect(method(HttpMethod.DELETE))
			.andRespond(withJson("get_customer_collection.json"));

		long customerId = 1234567;
		long productId = 987654;
			
		try {	
			service.updateFavoritesCollection(customerId, productId, false);	
			service.updateFavoritesCollection(customerId, productId, false);
			service.updateFavoritesCollection(customerId, productId, true);
			assertEquals(1, 1);
		} catch (Exception e) {
			assertEquals(1, 0);;
		};
	}

	@Test
	public void testGetFavProductList() {
		MockRestServiceServer mockShopifyServer = MockRestServiceServer.createServer(rest);

		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json?title=customer_1234567_favorites"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("get_customer_collection.json"));
		
		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "collects.json?collection_id=12345678"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("collect_collection.json"));
		
		mockShopifyServer
			.expect(requestTo(shopifyBaseUrl
					+ "custom_collections.json?title=customer_1234567_favorites"))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withJson("get_empty_customer_collection.json"));

		long customerId = 1234567;
		Long productID = (long) 38273302;
		Map<Long, Boolean> productIdList = service.getFavProductsMap(customerId);
		
		assertEquals(true, productIdList.get(productID));

		productIdList = service.getFavProductsMap(customerId);

		assertEquals(null, productIdList.get(productID));
	}
	
	@Test
	public void testForFetchingMetafieldsByKeyType() {
		
		MockRestServiceServer mockShopifyServer = MockRestServiceServer
				.createServer(rest);
		
		mockShopifyServer
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135042_metafields.json"));
		
		mockShopifyServer
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135042_metafields.json"));
		
		List<MetaField> preferenceMetafields = service.getMetaFieldsByKeyType(
				"customers", 338135042L, "preference");
		
		List<MetaField> followingMetafields = service.getMetaFieldsByKeyType(
				"customers", 338135042L, "following");
		assertEquals(preferenceMetafields.size(), 2);
		assertEquals(preferenceMetafields.get(0).getKey().split("_")[0], "preference");
		assertEquals(followingMetafields.size(), 2);
		assertEquals(followingMetafields.get(0).getKey().split("_")[0], "following");
	}
}

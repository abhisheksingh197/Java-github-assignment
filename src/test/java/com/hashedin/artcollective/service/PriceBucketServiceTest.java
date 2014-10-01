package com.hashedin.artcollective.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.repository.PriceBucketRepository;

@Ignore
public class PriceBucketServiceTest extends BaseUnitTest {
	
	private static boolean isInitialized = false;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private PriceBucketService priceBucketService;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private ShopifyService shopifyService;
	
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		
		PriceBucket priceBucketObj1 = new PriceBucket(1L,"low",2500,5000);
		priceBucketService.addPriceBucket(priceBucketObj1);
		isInitialized = true;
		
	}
	
	@Test
	public void testForAddingpriceBuckets() {
		List<PriceBucket> priceBuckets = (List<PriceBucket>) priceBucketRepository.findAll();
		assertEquals(priceBuckets.size(), 1);
	}
	
	@Test
	public void testForFindingProductPriceBuckets() {
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);

		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products/count.json?product_type=artworks"))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyCount(1));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks&limit=100&page=1"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artworksupdate.json"));
		
		DateTime lastModified = new DateTime();
		List<Product> p = shopifyService.getArtWorkProductsSinceLastModified(lastModified);
		
		List<PriceBucket> priceBuckets = priceBucketService.getPriceBuckets(p.get(0));
		
		assertEquals(priceBuckets.size(),1);
		
	}

}

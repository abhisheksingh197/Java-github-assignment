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
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;
import com.hashedin.artcollective.utils.SynchronizeSetup;


public class PriceBucketServiceTest extends BaseUnitTest {
	
	private static boolean isInitialized = false;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private PriceAndSizeBucketService priceAndSizeBucketService;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		synchronizeSetup.artworksSynchronizeSetup();
		isInitialized = true;
	}
	
	@Test
	public void testForAddingpriceBuckets() {
		List<PriceBucket> priceBuckets = (List<PriceBucket>) priceBucketRepository.findAll();
		assertEquals(priceBuckets.size(), 3);
	}
	
	@Test
	public void testForFindingProductPriceBuckets() {
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		String queryString = "";
		String lastUpdatedAt = "";
		DateTime lastModified = syncLogRepository.getLastSynchronizeDate("artworks");
		if (lastModified != null) {
			queryString = "&updated_at_min=";
			lastUpdatedAt = lastModified.toString();
		}
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products/count.json?product_type=artworks"
				.concat(queryString).concat(lastUpdatedAt.replace("+", "%2B"))))
				.andExpect(method(HttpMethod.GET))
				.andRespond(shopifyArtworksCount(1));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks"
				.concat(queryString).concat(lastUpdatedAt.replace("+", "%2B"))
				.concat("&limit=100&page=1")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artworks.json"));
		
		
		List<CustomCollection> p = shopifyService.getArtWorkProductsSinceLastModified(lastModified);
		PriceAndSizeBucket priceAndSizeBucket = priceAndSizeBucketService.getPriceAndSizeBuckets(p.get(0));
		List<PriceBucket> priceBuckets = priceAndSizeBucket.getPriceBuckets();
		List<SizeBucket> sizeBuckets = priceAndSizeBucket.getSizeBuckets();
		assertEquals(priceBuckets.size(),1);
		assertEquals(sizeBuckets.size(), 1);
		assertEquals(sizeBuckets.get(0).getTitle(), "small");
		
	}

}

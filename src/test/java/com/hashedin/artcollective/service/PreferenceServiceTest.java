package com.hashedin.artcollective.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class PreferenceServiceTest extends BaseUnitTest{
	
	@Autowired
	private PreferenceService preferenceService;
	
	@Autowired
	private RestTemplate rest;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Before
	public void setup() {
		synchronizeSetup.artworksSynchronizeSetup();
	}
	
	@Test
	public void testForFetchingUserPreferences() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135041_metafields.json"));
		
		Map<String, Object> userPreferences = preferenceService.getPreferencesForUser(338135041L);
		assertEquals(userPreferences.size(), 4);
		Map<String, Object> shopPreferences = preferenceService.getPreferencesForShop();
		assertEquals(shopPreferences.size(), 4);
	}
	
	@Test
	public void testForFetchArtworksByPreferencesForCustomer() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135041_metafields.json"));
		CriteriaSearchResponse searchResponse = preferenceService
				.getRecomendedArtworksForCustomer(338135041L, 40, 0);
		assertEquals(searchResponse.getArtworks().size(), 2);
		
	}
	
	@Test
	public void testForCreatingMetafieldsForCustomer() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135042_metafields.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("customer_338135042_metafields_style.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("customer_338135042_metafields_orientation.json"));
		
		CriteriaSearchResponse searchResponse = preferenceService
				.getRecomendedArtworksForCustomer(338135042L, 40, 0);
		assertEquals(searchResponse.getArtworks().size(), 2);
		
	}
	
	
}

package com.hashedin.artcollective.service;

import static org.junit.Assert.assertEquals;
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

public class FollowingServiceTest extends BaseUnitTest{
	
	@Autowired
	private RestTemplate rest;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Autowired
	private FollowingService followingService;
	
	@Before
	public void setup() {
		synchronizeSetup.artworksSynchronizeSetup();
	}
	
	@Test
	public void testForFetchingUserFollowings() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135041_metafields.json"));
		
		Map<String, Object> userFollowings = followingService.getFollowingsForUser(338135041L);
		assertEquals(userFollowings.size(), 4);
	}
	
	@Test
	public void testForUpdatingUserFollowings() {
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
				.andRespond(withJson("customer_338135042_metafields_subject.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("customer_338135042_metafields_artist.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields/14612341313.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields/143233.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields/953045731.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields/12332.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		String[] emptyFollowings = {"0"};
		boolean updated = followingService.updateFollowingsForUser(338135042L, emptyFollowings, 
				emptyFollowings, emptyFollowings, emptyFollowings);
		assertEquals(updated, true);
	}
	
	@Test
	public void testForIsCollectionFollowedByCustomer() {
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135041_metafields.json"));
		
		boolean isFollowed = followingService.isCollectionFollowedByCustomer(
				338135041L, 26109780L, "subject");
		assertEquals(isFollowed, true);
	}
	
	@Test
	public void testForUpdatingCustomerFollowingCollections() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135041_metafields.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135041/metafields/14234.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135042_metafields.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("customer_338135042_metafields.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("customer_338135042_metafields_subject.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("customer_338135042_metafields_artist.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "customers/338135042/metafields/953045731.json"))
				.andExpect(method(HttpMethod.PUT))
				.andRespond(withJson("put.json"));
		
		int removeResponse = followingService.toggleCollectionFollowedByCustomer(
				338135041L, 26109780L, "subject");
		
		int addResponse = followingService.toggleCollectionFollowedByCustomer(
				338135042L, 26109780L, "subject");
		
		assertEquals(removeResponse, 2);
		assertEquals(addResponse, 1);
	}
	
}

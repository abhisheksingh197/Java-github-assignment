package com.hashedin.artcollective.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseIntegrationTest;
import com.hashedin.artcollective.controller.ProductsAPI;
import com.hashedin.artcollective.entity.ArtWork;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ProductsAPITest extends BaseIntegrationTest {

	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private ProductsAPI productsAPI;
	

	@Test
	public void userCanSearchForArtWorksByColor() {
		
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "color_search/"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tin_eye_color_search_response.json"));

		given().sessionId(login("shopper", "shopper"))
				.when()
				.get("/api/artworks/search/color?colors=DF4F23&weights=70&colors=255,112,0&weights=30")
				.then().statusCode(200)
				.body(not(containsString("Access is denied")));

		String[] colors = { "DF4F23", "255,112,0" };
		int[] weights = { 1, 1 };
		Map<String, Object> artMap = productsAPI.getAllArtworksByColor(colors,
				weights);
		assertEquals(artMap.size(), 1);
	}
	
	
}

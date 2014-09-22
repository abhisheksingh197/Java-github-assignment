package com.hashedin.artcollective.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.FrameVariant;

public class FrameVariantServiceTest extends BaseUnitTest {
	
	@Autowired
	private FrameVariantService frameVariantService;
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private ArtWorksService artWorksService;
	
	private static boolean isInitialized = false;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);

		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("frames.json"));
		artWorksService.saveFramesModifiedSince(null);
		
	}
	
	@Test
	public void testForFramesSearch() {
		List<FrameVariant> ids = frameVariantService.getFrames(12.0, 16.0, 3.0, 4.0);
		assertEquals(ids.size(), 1);
	}
	
}

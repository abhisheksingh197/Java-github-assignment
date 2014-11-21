package com.hashedin.artcollective.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.ArrayList;
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
import com.hashedin.artcollective.utils.SynchronizeSetup;

public class FrameVariantServiceTest extends BaseUnitTest {
	
	@Autowired
	private FrameVariantService frameVariantService;
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private ArtWorksService artWorksService;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	private static boolean isInitialized = false;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		
		synchronizeSetup.artworksSynchronizeSetup();
		isInitialized = true;
	}
	
	@Test
	public void testForFramesSearch() {
		List<Frame> tempFrames = new ArrayList<>();
		List<FrameVariant> frameVariants = frameVariantService.getFrames(3.0, 4.0);
		for (FrameVariant frameVariant : frameVariants) {
			Frame frame = new Frame(frameVariant);
			frame.setFramePrice(frameVariantService.getFramePrice(12.00, 16.00, frameVariant));
			tempFrames.add(frame);
		}
		assertEquals(tempFrames.size(), 1);
		double framePrice = tempFrames.get(0).getFramePrice();
		assertEquals(framePrice, 650.00, 0.1);
	}
	
	@Test 
	public void testForFetchingCanvasPrice() {
		long canvasVariantId = 934449115L;
		double canvasPrice = frameVariantService.getCanvasPrice(12.00, 16.00, canvasVariantId);
		assertEquals(canvasPrice, 320.00, 0.1);
	}
	
	@Test
	public void testForCreatingDynamicProduct() {
		
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("create_dynamic_product_response.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("create_dynamic_product_response.json"));
			
		CustomCollection frameProduct = frameVariantService.createAddOnProduct(7964345234234L, 
					796611812L, "frame");
		
		CustomCollection canvasProduct = frameVariantService.createAddOnProduct(7964345234234L, 
				934449115L, "canvas");
		
		assertEquals(frameProduct.getProductType(), "dynamic");
		assertEquals(canvasProduct.getProductType(), "dynamic");
		
	}
	
}

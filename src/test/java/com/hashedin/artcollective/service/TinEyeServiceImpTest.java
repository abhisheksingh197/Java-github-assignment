package com.hashedin.artcollective.service;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Image;

public class TinEyeServiceImpTest extends BaseUnitTest {

	@Autowired
	private TinEyeServiceImpl tineye;
	
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;

	
	@Test
	public void testForArtworkUpload() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "add"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tineye_add_response.json"));
		
		Image image = new Image();
		image.setId(15L);
		image.setImgSrc("www.example.com/first.img");
		image.setProductId(42L);
		List<Image> images = new ArrayList<>();
		images.add(image);
		ArtWork art = new ArtWork();
		art.setId(42L);
		art.setImages(images);
		List<ArtWork> arts = new ArrayList<>();
		arts.add(art);
		tineye.uploadArts(arts);
	}
	
	@Test
	public void testForGetMatchingArtworkByColor() {
		
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "color_search/"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tin_eye_color_search_response.json"));
		
		
		ArtSearchCriteria artSearchCiteria = new ArtSearchCriteria();
		artSearchCiteria.setColor1Weight(1);
		artSearchCiteria.setColor2Weight(1);
		artSearchCiteria.setColour1("255,255,255");
		artSearchCiteria.setColour2("0,0,0");
		List<ArtWork> artworks = tineye.getMatchingArtWorks(artSearchCiteria);
		assertEquals(artworks.size(),2);
	}
	
	@Test
	public void testForGetImageColors() {
		
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "extract_image_colors/"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tin_eye_color_extract_response.json"));
		
		String imgUrl = "www.example.com/first.img";	
		String exractedColors = tineye.getImageColors(imgUrl);
		assertEquals(exractedColors, "11d45f,edc9af");

		
	}
	

}

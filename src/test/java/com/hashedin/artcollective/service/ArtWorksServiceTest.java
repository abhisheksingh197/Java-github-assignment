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
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Image;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.utils.SynchronizeSetup;



public class ArtWorksServiceTest extends BaseUnitTest {

	// Comment saveToTinEye Method in ArtworksService before running Test Cases.
	
	private static boolean isInitialized = false;
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private ArtWorksService service;

	@Autowired
	private ShopifyService shopifyService;

	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired 
	private ArtWorkRepository artRepository;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private ArtWorksSearchService searchService;
	
	@Autowired
	private PriceAndSizeBucketService priceBucketService;
	
	@Autowired
	private SynchronizeSetup synchronizeSetup;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		synchronizeSetup.artworksSynchronizeSetup();
		isInitialized = true;
		
	}
	@Test
	public void testThatArtWorksWereUpdated() {
		List<ArtWork> artList = (List<ArtWork>) artRepository.findAll();
		ArtWork artwork = artList.get(0);
		assertEquals(artwork.getTitle(), "India Gate");
		assertEquals(artList.size(),3);
	}
	
	@Test
	public void testThatArtistFirstAndLastNameIsAdded() {
		List<ArtWork> artList = (List<ArtWork>) artRepository.findAll();
		ArtWork artwork = artList.get(0);
		assertEquals(artwork.getArtist().getFirstName(), "Amit");
		assertEquals(artwork.getArtist().getLastName(), "Bhar");
	}
	
	@Test
	public void testForSearchByArtist() {
		List<ArtWork> artWorkList = searchService.findArtworksByArtist("Amit");
		assertEquals(artWorkList.size(), 3);
	}
	
	@Test
	public void testForSearchByCriteria() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
		.expect(requestTo(tinEyeBaseUrl + "color_search/"))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withJson("tin_eye_color_search_response.json"));
		
		List<String> subjectList = new ArrayList<>();
		subjectList.add("26109780");

		
		List<String> styleList = new ArrayList<>();
		styleList.add("12345");
		String[] colorsList = new String[2];
		colorsList[0] = "FFFFFF";
		List<String> priceBucketRangeList = new ArrayList<>();
		priceBucketRangeList.add("1");
		List<String> sizeBucketRangeList = new ArrayList<>();
		sizeBucketRangeList.add("1");
		String medium = "paper";
		String orientation = "landscape";
		int offset = 0;
		int limit = 3;
		CriteriaSearchResponse searchResponse = searchService.findArtworksByCriteria(
				subjectList,
				styleList,
				colorsList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		assertEquals(searchResponse.getArtworks().size(), 1);
	}
	
	@Test
	public void testForSearchByNullCriteria() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
		.expect(requestTo(tinEyeBaseUrl + "color_search/"))
		.andExpect(method(HttpMethod.POST))
		.andRespond(withJson("tin_eye_color_search_response.json"));
		
		List<String> subjectList = new ArrayList<>();
		subjectList.add("-1");
		List<String> styleList = new ArrayList<>();
		styleList.add("-1");
		String[] colorsList = new String[2];
		colorsList[0] = "FFFFFF";
		List<String> priceBucketRangeList = new ArrayList<>();
		priceBucketRangeList.add("-1");
		List<String> sizeBucketRangeList = new ArrayList<>();
		sizeBucketRangeList.add("-1");
		String medium = null;
		String orientation = null;
		int offset = 0;
		int limit = 3;
		CriteriaSearchResponse searchResponse = searchService.findArtworksByCriteria(
				subjectList,
				styleList,
				colorsList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		assertEquals(searchResponse.getArtworks().size(), artRepository.count());
	}
	
	@Test
	public void testForSearchByColor() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "color_search/"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tin_eye_color_search_response.json"));
		
		
		String[] colors = {"255,255,255","0,0,0"};
		int[] weights = {1,1};
		CriteriaSearchResponse searchResponse = searchService.findArtworksByColor(colors, weights);
		assertEquals(searchResponse.getArtworks().size(), 3);
	}
	
	@Test
	public void testForSearchBySingleColor() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
				.expect(requestTo(tinEyeBaseUrl + "color_search/"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tin_eye_color_search_response.json"));
		
		String[] colors = {"255,255,255"};
		int[] weights = {1};
		CriteriaSearchResponse searchResponse = searchService.findArtworksByColor(colors, weights);
		assertEquals(searchResponse.getArtworks().size(), 3);
	}
	
	@Test
	public void testForPriceBucket() {
		List<ArtWork> artList = (List<ArtWork>) artRepository.findAll();
		ArtWork artwork = artList.get(0);
		assertEquals(artwork.getPriceBuckets().size(), 1);
		List<PriceBucket> priceBucket = (List<PriceBucket>) priceBucketRepository.findAll();
		assertEquals(priceBucket.size(), 3);
	}
	
	@Test
	public void testForResizeImageWidthandHeight() {
		ArtWork art = artRepository.findOne(504096747L);
		List<Image> images = art.getImages();
		for (Image image : images) {
			if (image.getImgSrc().contains("-artfinder")) {
				assertEquals(image.getWidth(), 198);
			}
		}
	}
	
	@Test 
	public void testThatArtworksAreReturnedInTheOrderByColorMatch() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
			.expect(requestTo(tinEyeBaseUrl + "color_search/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_search_response.json"));
		
		List<String> subjectList = new ArrayList<>();
		subjectList.add("26109780");

		
		List<String> styleList = new ArrayList<>();
		styleList.add("12345");
		String[] colorsList = new String[2];
		colorsList[0] = "FFFFFF";
		List<String> priceBucketRangeList = new ArrayList<>();
		priceBucketRangeList.add("1");
		List<String> sizeBucketRangeList = new ArrayList<>();
		sizeBucketRangeList.add("1");
		String medium = "paper";
		String orientation = "landscape";
		int offset = 0;
		int limit = 3;
		CriteriaSearchResponse searchResponse = searchService.findArtworksByCriteria(
				subjectList,
				styleList,
				colorsList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		ArtWork firstArtWork = searchResponse.getArtworks().get(0);
		long artID = firstArtWork.getId();
		assertEquals(artID, 504096747);
	}

	@Test 
	public void testThatArtworksAreReturnedInTheOrderByCreatedAt() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
			.expect(requestTo(tinEyeBaseUrl + "color_search/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_search_response.json"));
		
		List<String> subjectList = new ArrayList<>();
		subjectList.add("-1");
		List<String> styleList = new ArrayList<>();
		styleList.add("-1");
		String[] colorsList = new String[2];
		colorsList = null;
		List<String> priceBucketRangeList = new ArrayList<>();
		priceBucketRangeList.add("-1");
		List<String> sizeBucketRangeList = new ArrayList<>();
		sizeBucketRangeList.add("1");
		String medium = null;
		String orientation = null;
		int offset = 0;
		int limit = 3;
		CriteriaSearchResponse searchResponse = searchService.findArtworksByCriteria(
				subjectList,
				styleList,
				colorsList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		long artID = searchResponse.getArtworks().get(0).getId();
		assertEquals(artID, 504096747);
	}
	
	
	@Test
	public void testThatArtworksHaveTheRightSizeRange() {
		MockRestServiceServer mockTinEyeService = MockRestServiceServer
				.createServer(rest);
		mockTinEyeService
			.expect(requestTo(tinEyeBaseUrl + "color_search/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_search_response.json"));
		
		List<String> subjectList = new ArrayList<>();
		subjectList.add("26109780");

		
		List<String> styleList = new ArrayList<>();
		styleList.add("12345");
		String[] colorsList = new String[2];
		colorsList[0] = "FFFFFF";
		List<String> priceBucketRangeList = new ArrayList<>();
		priceBucketRangeList.add("-1");
		List<String> sizeBucketRangeList = new ArrayList<>();
		sizeBucketRangeList.add("3");
		String medium = "paper";
		String orientation = "landscape";
		int offset = 0;
		int limit = 3;
		CriteriaSearchResponse searchResponse = searchService.findArtworksByCriteria(
				subjectList,
				styleList,
				colorsList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		ArtWork firstArtWork = searchResponse.getArtworks().get(0);
		SizeBucket sizeBucket = firstArtWork.getSizeBuckets().get(0);
		PriceBucket priceBucket = firstArtWork.getPriceBuckets().get(0);
		assertEquals(sizeBucket.getTitle(), "large");
		assertEquals(priceBucket.getTitle(), "average");
	}
	
	@Test 
	public void testForActualCountOfArtists() {
		assertEquals(artistRepository.count(), 2);
	}
	

}

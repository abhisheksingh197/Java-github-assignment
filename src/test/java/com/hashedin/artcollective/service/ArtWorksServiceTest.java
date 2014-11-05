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
import com.hashedin.artcollective.repository.PriceBucketRepository;



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
	
	@Before
	public void setup() {
		if(isInitialized) {
			return;
		}
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);

		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products/count.json?product_type=artworks"))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyArtworksCount(7));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks&limit=100&page=1"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artworks.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=343096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_343096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/343096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_343096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=343096748"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_343096748.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/343096748/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_343096748.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=503096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_503096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/503096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_503096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=513096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_513096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/513096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_513096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=523096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_523096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/523096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_523096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=533096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_533096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/533096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_533096747.json"));
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=504096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_504096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_504096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("image_upload_response_504096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("image_upload_response_504096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=505096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_505096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/505096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_505096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/505096747/images.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("image_upload_response_505096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=506096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_506096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/506096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_506096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/506096747/images.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("image_upload_response_506096747.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/506096747/images.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("image_upload_response_506096747.json"));
				
		mockArtWorksService
				.expect(requestTo(tinEyeBaseUrl + "add"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tineye_add_response.json"));
		
		
		 mockArtWorksService
			.expect(requestTo(tinEyeBaseUrl + "extract_image_colors/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_extract_response.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("color_extract_metafield.json"));
		
		mockArtWorksService
			.expect(requestTo(tinEyeBaseUrl + "extract_image_colors/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_extract_response.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/505096747/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("color_extract_metafield.json"));
		
		mockArtWorksService
			.expect(requestTo(tinEyeBaseUrl + "extract_image_colors/"))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withJson("tin_eye_color_extract_response.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/506096747/metafields.json"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("color_extract_metafield.json"));
		
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("frames.json"));
		
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks&limit=100"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artworksupdate.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections.json?product_id=343096747"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("collections_343096747.json"));

		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/343096747/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("metafields_343096747.json"));
		
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("frames.json"));
		
		
		PriceBucket priceBucketObj1 = new PriceBucket(1L,"low",2500.00,5000.00);
		priceBucketService.addPriceBucket(priceBucketObj1);
		PriceBucket priceBucketObj2 = new PriceBucket(2L,"medium",5001.00, 7500.00);
		priceBucketService.addPriceBucket(priceBucketObj2);
		PriceBucket priceBucketObj3 = new PriceBucket(3L,"average",7501.00, null);
		priceBucketService.addPriceBucket(priceBucketObj3);
		SizeBucket sizeBucketObj1 = new SizeBucket(1L,"small",0.0,400.0);
		priceBucketService.addSizeBucket(sizeBucketObj1);
		sizeBucketObj1 = new SizeBucket(2L,"medium",401.0,900.0);
		priceBucketService.addSizeBucket(sizeBucketObj1);
		sizeBucketObj1 = new SizeBucket(3L,"large",901.0, null);
		priceBucketService.addSizeBucket(sizeBucketObj1);


		service.synchronize(null);
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
	

}

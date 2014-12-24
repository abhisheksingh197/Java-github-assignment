package com.hashedin.artcollective.service;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtCollection;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.Artist;
import com.hashedin.artcollective.entity.Image;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.FrameVariantRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;
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
	
	@Autowired
	private ArtCollectionsRepository artCollectionRepository;
	
	@Autowired
	private FrameVariantRepository frameVariantRepository;
	
	@Autowired
	private SynchronizeLogRepository syncLogRepository;
	
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
				.andRespond(shopifyArtworksCount(7));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=artworks"
				.concat(queryString).concat(lastUpdatedAt.replace("+", "%2B"))
				.concat("&limit=100&page=1")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artworksupdate.json"));

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
						+ "variants/79643453611812/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("variant_metafields.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images/80834534669747.json"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images/818663453459750.json"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images/81866345345350.json"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withJson("put.json"));
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images/809747.json"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withJson("put.json"));
		
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
				.expect(requestTo(tinEyeBaseUrl + "add"))
				.andExpect(method(HttpMethod.POST))
				.andRespond(withJson("tineye_add_response.json"));
		
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("frames.json"));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=canvas"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("canvas.json"));
		service.synchronize(null);
		List<ArtWork> artList = (List<ArtWork>) artRepository.findAll();
		ArtWork artwork = artRepository.findOne(504096747L);
		assertEquals(artwork.getTitle(), "Changed Title");
		long featuredImageId = artwork.getFeaturedImageId();
		assertEquals(featuredImageId, 809747);
		assertEquals(artList.size(),3);
		artwork.setFeaturedImageId(8086634539747L);
		artRepository.save(artwork);
	}
	
	@Test
	public void testForArtistDetails() {
		List<ArtWork> artList = (List<ArtWork>) artRepository.findAll();
		ArtWork artwork = artList.get(0);
		Artist artist = artwork.getArtist();
		assertEquals(artist.getFirstName(), "Sunil");
		assertEquals(artist.getLastName(), "Sarkar");
		assertEquals(artist.getEmail(), "sunil.sarkar@example.com");
		assertEquals(artist.getContactNumber(), "080 - 2123432");
		assertEquals(artist.getUsername(), "sunil.sarkar");
		assertEquals(artist.getPassword(), "sunil.sarkar@123");
		assertEquals(artist.getImgSrc(), "https://cdn.shopify.com/s/files/1/0608/8161/collections/Bharti_Prajapati.png?v=1411264259");
		
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
		priceBucketRangeList.add("3");
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
		int totalArtsCount = 0;
		for (ArtWork art : artRepository.findAll()) {
			if (art.isDeleted() == false) {
				totalArtsCount++;
			}
		}
		assertEquals(searchResponse.getArtworks().size(), totalArtsCount);
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
		ArtWork art = artRepository.findOne(505096747L);
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
		priceBucketRangeList.add("3");
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
		long artID = firstArtWork.getId();
		assertEquals(artID, 505096747);
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
		assertEquals(artID, 506096747);
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
	
	@Test
	public void testForMultiWordCollectionName() {
		ArtCollection collection = artCollectionRepository.findOne(678911L);
		assertEquals(collection.getTitle(), "independence day special");
	}
	
	@Test
	public void testForProductDelete() {
		long pid = 504096747;
		List<ArtWork> artworks = (List<ArtWork>) artRepository.findAll();
		assertEquals(artworks.get(0).isDeleted(), false);
		pid = artworks.get(0).getId();
		service.deleteProduct(pid);
		assertEquals(artRepository.findOne(pid).isDeleted(), true);
	}

}

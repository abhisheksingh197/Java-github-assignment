package com.hashedin.artcollective.utils;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SynchronizeLogRepository;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.OrdersService;
import com.hashedin.artcollective.service.PriceAndSizeBucketService;
import com.hashedin.artcollective.service.ShopifyService;

@Service
public class SynchronizeSetup extends BaseUnitTest {

	// Comment saveToTinEye Method in ArtworksService before running Test Cases.
	
	private static boolean isInitialized = false;
	@Value("${shopify.baseurl}")
	private String shopifyBaseUrl;

	@Value("${tinEye.baseurl}")
	private String tinEyeBaseUrl;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private ArtWorksService artworksService;
	
	@Autowired
	private OrdersService ordersService;

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
	private SynchronizeLogRepository syncLogRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	
	public void artworksSynchronizeSetup() {
		if(isInitialized) {
			return;
		}
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
		
		if (artistRepository.findArtistByCollectionID(26109781L) == null) {
			mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections/26109781/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artist_26109781_metafields.json"));
			mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections/26109781.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artist_collection.json"));
		}

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
		
		if (artistRepository.findArtistByCollectionID(26209781L) == null) {
			mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections/26209781/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artist_26209781_metafields.json"));
			mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "custom_collections/26209781.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("artist_collection.json"));
		}
		
		mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "variants/79643453611812/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("variant_metafields.json"));
		ArtWork existingArtwork = artRepository.findOne(504096747L);
		if (existingArtwork != null) {
			long existingImageId = existingArtwork.getFeaturedImageId();
			if (existingImageId != 8086634539747L) {
				mockArtWorksService
				.expect(requestTo(shopifyBaseUrl
						+ "products/504096747/images/1001473899.json"))
				.andExpect(method(HttpMethod.DELETE))
				.andRespond(withJson("image_upload_response_504096747.json"));
		
				mockArtWorksService
						.expect(requestTo(shopifyBaseUrl
								+ "products/504096747/images/1001473899.json"))
						.andExpect(method(HttpMethod.DELETE))
						.andRespond(withJson("image_upload_response_504096747.json"));
			}
		}
		
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
						+ "variants/7964345234234/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("variant_metafields.json"));
		
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
						+ "variants/12312334234/metafields.json"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("variant_metafields.json"));
		
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
		
		if (artRepository.count() == 0) {
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
		}
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=frames"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("frames.json"));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "products.json?product_type=canvas"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withJson("canvas.json"));
		
		
		
		
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


		artworksService.synchronize(null);
	}


	public void ordersSynchronizeSetup() {
		MockRestServiceServer mockArtWorksService = MockRestServiceServer
				.createServer(rest);
		
		String queryString = "";
		String lastUpdatedAt = "";
		DateTime lastModified = syncLogRepository.getLastSynchronizeDate("orders");
		if (lastModified != null) {
			queryString = "&updated_at_min=";
			lastUpdatedAt = lastModified.toString();
		}
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders/count.json?fulfillment_status=shipped"
				.concat(queryString).concat(lastUpdatedAt.replace("+", "%2B"))))
		.andExpect(method(HttpMethod.GET))
		.andRespond(shopifyOrdersCount(5));
		
		mockArtWorksService.expect(requestTo(shopifyBaseUrl + "orders.json?fulfillment_status=shipped"
				.concat(queryString).concat(lastUpdatedAt.replace("+", "%2B"))
				.concat("&limit=100&page=1")))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withJson("orders.json"));
		
		ordersService.synchronize(null);
		
	}

}

package com.hashedin.artcollective.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.ArtworkVariant;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.ShopifyWebHook;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtworkVariantRepository;
import com.hashedin.artcollective.repository.FulfilledOrderRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.ShopifyWebHookRepository;
import com.hashedin.artcollective.repository.SizeBucketRepository;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.CriteriaSearchResponse;
import com.hashedin.artcollective.service.CustomCollection;
import com.hashedin.artcollective.service.DeductionsService;
import com.hashedin.artcollective.service.FollowingService;
import com.hashedin.artcollective.service.Frame;
import com.hashedin.artcollective.service.FrameVariantService;
import com.hashedin.artcollective.service.OrdersService;
import com.hashedin.artcollective.service.PreferenceService;
import com.hashedin.artcollective.service.PriceAndSizeBucketService;
import com.hashedin.artcollective.service.ShopifyService;
import com.hashedin.artcollective.service.Style;
import com.hashedin.artcollective.service.Subject;
import com.hashedin.artcollective.service.TinEyeService;
import com.hashedin.artcollective.service.TransactionsService;
import com.hashedin.artcollective.service.WebHookResponse;
import com.hashedin.artcollective.utils.ProductSize;

@RestController
public class ProductsAPI {

	@Autowired
	private ArtWorksService artworkService;
	
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private ArtWorksSearchService artworksSearchService;
	
	@Autowired
	private FrameVariantService frameVariantService;
	
	@Autowired
	private PriceAndSizeBucketService priceAndSizeBucketService;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	@Autowired
	private TinEyeService tinEyeService;
	
	@Autowired
	private ArtWorkRepository artWorkRepository;
	
	@Autowired
	private SizeBucketRepository sizeBucketRepository;
	
	@Autowired
	private TransactionsService transactionService;
	
	@Autowired
	private DeductionsService deductionService;
	
	@Autowired
	private ArtworkVariantRepository artworkVariantRepository;
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private PreferenceService preferenceService;
	
	@Autowired
	private FollowingService followingService;
	
	@Autowired
	private FulfilledOrderRepository fulfilledOrderRepository;
	
	@Autowired 
	private ShopifyWebHookRepository shopifyWebHookRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAPI.class);
	
	//Add Price Range Bucket
	@RequestMapping(value = "/admin/priceRange/add", method = RequestMethod.GET)
	public void addPriceBucket(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "lowerRange", required = true) Double lowerRange,
			@RequestParam(value = "upperRange", required = false) Double upperRange) {
		PriceBucket priceBucket = new PriceBucket(id, title, lowerRange, upperRange);
		priceAndSizeBucketService.addPriceBucket(priceBucket);
		LOGGER.info("Price Bucket: " + priceBucket.getTitle() + " Successfully Added");
	
	}
	
	@RequestMapping(value = "/api/upload/transactions", method = RequestMethod.POST)
    public List<String> transactionsCSVUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Reader csvReader = new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8"));
				List<String> errors = transactionService.saveTransactionsInBulk(csvReader);
                return errors;
            } 
            catch (Exception e) {
                return Arrays.asList("You failed to upload ");
            }
        } 
        else {
            return Arrays.asList("You failed to upload  because the file was empty.");
        }
    }
	
	@RequestMapping(value = "/api/upload/deductions", method = RequestMethod.POST)
    public List<String> deductionsCSVUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Reader csvReader = new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8"));
				List<String> errors = deductionService.saveDeductionsInBulk(csvReader);
                return errors;
            } 
            catch (Exception e) {
                return Arrays.asList("You failed to upload ");
            }
        } 
        else {
            return Arrays.asList("You failed to upload  because the file was empty.");
        }
    }
	
	//Add Size Range Bucket
		@RequestMapping(value = "/admin/sizeRange/add", method = RequestMethod.GET)
		public void addSizeBucket(
				@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "title", required = true) String title,
				@RequestParam(value = "lowerValue", required = true) Double lowerRange,
				@RequestParam(value = "upperValue", required = false) Double upperRange) {
			SizeBucket sizeBucket = new SizeBucket(id, title, lowerRange, upperRange);
			priceAndSizeBucketService.addSizeBucket(sizeBucket);
			LOGGER.info("Price Bucket: " + sizeBucket.getTitle() + " Successfully Added");
		
		}
	
	
	@RequestMapping(value = "/admin/priceRange/getall", method = RequestMethod.GET)
	public List<PriceBucket> getAllPriceBuckets() {
		
		return (List<PriceBucket>) priceBucketRepository.findAll();
	}
	
	@RequestMapping(value = "/admin/sizeRange/getall", method = RequestMethod.GET)
	public List<SizeBucket> getAllSizeBuckets() {
		
		return (List<SizeBucket>) sizeBucketRepository.findAll();
	}
	
	// Synchronize data from Shopify into internal Database and Tin Eye
	@RequestMapping(value = "/api/shopify/synchronize", method = RequestMethod.GET)
	public void synchronize(
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "mode", required = false) String mode) {
		if (type.equalsIgnoreCase("artworks")) {
			LOGGER.info("Artworks Synchronize Started");
			artworkService.synchronize(mode);
			LOGGER.info("Artworks Successfully Synchronized");
		}
		else if (type.equalsIgnoreCase("orders")) {
			LOGGER.info("Orders Synchronize Started");
			ordersService.synchronize(mode);
			LOGGER.info("Orders Successfully Synchronized");
		}
		/**
		 * Addons - Additional products that are purchased along with an artwork
		 * currently frames and canvas are considered as addons. In future if 
		 * there are new miscellaneous products coming up we can categorize them under this.
		 */
		else if (type.equalsIgnoreCase("addons")) {
			LOGGER.info("Add-Ons Synchronize Started");
			artworkService.saveAddOnsModifiedSince(null);
			LOGGER.info("Add-Ons Successfully Synchronized");
		}
		
	}
	
	/*
	 * Adding POST API requests for supporting WebHooks from Shopify that helps us
	 * handle Auto-Synchronization, and Deletion of products on Shopify.  
	 */
	@RequestMapping(value = "/api/shopify/webhook", method = RequestMethod.POST)
	public void shopifyWebHookhandling(
			@RequestParam(value = "event", required = true) String event,
			@RequestBody WebHookResponse webHookResponse,
			@RequestHeader(value = "X-Shopify-Hmac-Sha256") String webHookUniqueKey) {
		if (webHookUniqueKey == null) {
			return;
		}
		String productType = webHookResponse.getProductType() != null 
				? webHookResponse.getProductType() : "orders";
		Long productId = webHookResponse.getId();
		if (!webHookExists(webHookUniqueKey, event, productType)) {
			if (event.equalsIgnoreCase("update") || event.equalsIgnoreCase("create")) {
				LOGGER.info("Web Hook Update Started");
				if (productType.equalsIgnoreCase("orders")) {
					ordersService.synchronize(null);
				}
				else if (productType.equalsIgnoreCase("artworks") 
						|| productType.equalsIgnoreCase("frames") 
						|| productType.equalsIgnoreCase("canvas")) {
					artworkService.synchronize(null);
				}
				LOGGER.info("Web Hook Update Completed");
			}
			else if (event.equalsIgnoreCase("delete")) {
				LOGGER.info("Web Hook Delete Started");
					artworkService.deleteProduct(productId);
				LOGGER.info("Web Hook Delete Completed");
			}
		}
		
	}
	
	private boolean webHookExists(String webHookUniqueKey, String event, String productType) {
		int webHookCount = shopifyWebHookRepository.getWebHookCountByUniqueKey(webHookUniqueKey);
		if (webHookCount == 0) {
			ShopifyWebHook webHook = new ShopifyWebHook(DateTime.now(), 
					webHookUniqueKey, productType, event);
			shopifyWebHookRepository.save(webHook);
			return false;
		}
		return true;
	}

	// Search Artworks based on criteria
	//CHECKSTYLE:OFF
	@RequestMapping(value = "/api/artworks/search", method = RequestMethod.GET)
	public Map<String, Object> getAllArtworksByCriteria(
			@RequestParam(value = "subjects", required = false) String[] subjects,
			@RequestParam(value = "styles", required = false) String[] styles,
			@RequestParam(value = "colors", required = false) String[] colors,
			@RequestParam(value = "priceBucketRange", required = false) String[] priceBucketRange,
			@RequestParam(value = "medium", required = false) String medium,
			@RequestParam(value = "orientation", required = false) String orientation,
			@RequestParam(value = "sizeRange", required = false) String sizeRange,
			@RequestParam(value = "limit", required = true) Integer limit,
			@RequestParam(value = "offset", required = true) Integer offset,
			@RequestParam(value = "customerId", required = false) Long customerId) {
	//CHECKSTYLE:ON
		List<String> subjectList = new ArrayList<>();
		List<String> styleList = new ArrayList<>();
		List<String> colorsList = new ArrayList<>();
		List<String> priceBucketRangeList = new ArrayList<>();
		List<String> sizeBucketRangeList = new ArrayList<>();
 		subjectList.add("-1");
		styleList.add("-1");
		colorsList.add("");
		priceBucketRangeList.add("-1");
		sizeBucketRangeList.add("-1");
		subjectList = subjects != null ? Arrays.asList(subjects) : subjectList;
		styleList = styles != null ? Arrays.asList(styles) : styleList;
		colors = colors != null ? colors : null;
		medium = (medium !=  null) && (!medium.equalsIgnoreCase("")) ? medium : null;
		orientation = (orientation !=  null) && (!orientation.equalsIgnoreCase("")) ? orientation : null;
		priceBucketRangeList = priceBucketRange != null ? Arrays.asList(priceBucketRange) 
				: priceBucketRangeList;
		sizeBucketRangeList = sizeRange != null ? Arrays.asList(sizeRange) 
				: sizeBucketRangeList;
		CriteriaSearchResponse searchResponse = artworksSearchService.findArtworksByCriteria(
				subjectList, 
				styleList,
				colors,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				limit,
				offset);
		Map<String, Object> wrapResponse = wrapResponse(searchResponse);
		
		if (customerId != null) { 
			Map<Long, Boolean>	favouriteMap = shopifyService.getFavProductsMap(customerId);
			if (favouriteMap.size() > 0) {
				wrapResponse.put("favProductIdMap", favouriteMap);
			}
		} 
			
		return wrapResponse;
	}
	
	// Search Tin Eye based on color Criteria
	@RequestMapping(value = "/api/artworks/search/color", method = RequestMethod.GET)
	public Map<String, Object> getAllArtworksByColor(@RequestParam(value = "colors") 
		String[] colors, @RequestParam(value = "weights", required = false) int[] weights) {
		CriteriaSearchResponse searchResponse = artworksSearchService.findArtworksByColor(colors, weights);
		return wrapResponse(searchResponse);
	}
	
	// Search For Subjects and Styles
	@RequestMapping(value = "/api/collections", method = RequestMethod.GET)
	public Map<String, Object> getAllSubjects() {
		List<ArtSubject> subjects = (List<ArtSubject>) artSubjectRepository.findAll();
		List<Subject> tempSubjects = new ArrayList<>();
		for (ArtSubject subj : subjects) {
		    tempSubjects.add(new Subject(subj));
		}
		List<ArtStyle> styles = (List<ArtStyle>) artStyleRepository.findAll();
		List<Style> tempStyles = new ArrayList<>();
		for (ArtStyle style : styles) {
		    tempStyles.add(new Style(style));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjects", tempSubjects);
		map.put("styles", tempStyles);
		return map;
	}
	
	@RequestMapping(value = "/api/frames", method = RequestMethod.GET)
	public List<Frame> getFrames(
			@RequestParam(value = "frameLength", required = true) Double frameLength,
			@RequestParam(value = "frameBreadth", required = true) Double frameBreadth,
			@RequestParam(value = "mountThickness", required = true) Double mountThickness,
			@RequestParam(value = "frameThickness", required = true) Double frameThickness
			) {
		List<Frame> tempFrames = new ArrayList<>();
		List<FrameVariant> frameVariants = frameVariantService.getFrames(mountThickness, frameThickness);
		for (FrameVariant frameVariant : frameVariants) {
			Frame frame = new Frame(frameVariant);
			frame.setFramePrice(frameVariantService.getFramePrice(frameLength, frameBreadth, frameVariant));
			tempFrames.add(frame);
		}
		return tempFrames;
	}
	
	@RequestMapping(value = "/api/canvas/price", method = RequestMethod.GET)
	public Double getCanvasPrice(
			@RequestParam(value = "productVariantId", required = true)Long productVariantId,
			@RequestParam(value = "canvasVariantId", required = true)Long canvasVariantId) {
		ArtworkVariant artworkVariant = artworkVariantRepository.findOne(productVariantId);
		if (artworkVariant != null) {
			ProductSize productSize = new ProductSize(artworkVariant);
			return frameVariantService.getCanvasPrice(productSize.getProductLength(), 
					productSize.getProductBreadth(), canvasVariantId);
		}
		return null;
		
	}
	
	@RequestMapping(value = "/api/uploadImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
	@ResponseBody String extractColorsFromImage(@RequestParam("file") MultipartFile file) throws IOException {
		File tempFile = File.createTempFile("uploadedfile", null);
		file.transferTo(tempFile);
		String colors = tinEyeService.extractColorUploadImage(tempFile);
		return colors;
    }
	
	@RequestMapping(value = "/api/uploadFileBase64", method = RequestMethod.POST)
	@ResponseBody String extractColorsFromBase64Image(@RequestParam("fileBase64") String fileBase64)
			throws IOException {
		String temp = fileBase64.split(",")[1].trim();
		byte imageBytes[] = Base64.decodeBase64(temp);
		File tempFile = File.createTempFile("uploadedfile", null);
		OutputStream output = null;
		try {
			output = new FileOutputStream(tempFile);
			IOUtils.write(imageBytes, output);
		}
		finally {
			IOUtils.closeQuietly(output);
		}
		String colors = tinEyeService.extractColorUploadImage(tempFile);
		return colors;
    }
	
	@RequestMapping(value = "/api/createProduct", method = RequestMethod.GET)
	public CustomCollection createDynamicProduct(
			@RequestParam(value = "productVariantId", required = true)Long productVariantId,
			@RequestParam(value = "typeVariantId", required = true)Long typeVariantId, 
			@RequestParam(value = "type", required = true)String type) {
		
			return frameVariantService.createAddOnProduct(productVariantId, 
					typeVariantId, type);
		
	}
	
	@RequestMapping(value = "/api/customer/preferences", method = RequestMethod.GET)
	public Map<String, Object> getCustomerPreferences(
			@RequestParam(value = "customerId", required = true)Long customerId) {
		Map<String, Object> shopPreferences = preferenceService.getPreferencesForShop();
		Map<String, Object> customerPreferences = preferenceService.getPreferencesForUser(customerId);
		Map<String, Object> preferences = new HashMap<>();
		preferences.put("shopPreferences", shopPreferences);
		preferences.put("customerPreferences", customerPreferences);
		
		return preferences;
	}
	
	@RequestMapping(value = "/api/customer/preferences", method = RequestMethod.POST)
	public Boolean modifyCustomerPreferences(
			@RequestParam(value = "customerId", required = true)Long customerId,
			@RequestParam(value = "subjects", required = true)String[] subjects,
			@RequestParam(value = "styles", required = true)String[] styles,
			@RequestParam(value = "mediums", required = true)String[] mediums,
			@RequestParam(value = "orientations", required = true)String[] orientations) {
		return preferenceService.updatePreferencesForUser(customerId, 
				subjects, styles, mediums, orientations);
	}
	
	@RequestMapping(value = "/api/customer/followings", method = RequestMethod.GET)
	public Map<String, Object> getCustomerFollowings(
			@RequestParam(value = "customerId", required = true)Long customerId) {
		Map<String, Object> customerFollowings = followingService.getFollowingsForUser(customerId);
		Map<String, Object> followings = new HashMap<>();
		followings.put("customerFollowings", customerFollowings);
		return followings;
	}
	
	@RequestMapping(value = "/api/customer/followings", method = RequestMethod.POST)
	public Boolean modifyCustomerFollowings(
			@RequestParam(value = "customerId", required = true)Long customerId,
			@RequestParam(value = "subjects", required = true)String[] subjects,
			@RequestParam(value = "styles", required = true)String[] styles,
			@RequestParam(value = "collections", required = true)String[] collections,
			@RequestParam(value = "artists", required = true)String[] artists) {
		return followingService.updateFollowingsForUser(customerId, 
				subjects, styles, collections, artists);
	}
	
	@RequestMapping(value = "/api/customer/following/collection", method = RequestMethod.GET)
	public Boolean getCustomerFollowingCollection(
			@RequestParam(value = "customerId", required = true)Long customerId, 
			@RequestParam(value = "collectionId", required = true)Long collectionId,
			@RequestParam(value = "collectionType", required = true)String collectionType) {
		
		return followingService.isCollectionFollowedByCustomer(customerId, collectionId, collectionType);
	};
	
	@RequestMapping(value = "/api/customer/following/collection", method = RequestMethod.POST)
	public Integer updateCustomerFollowingCollection(
			@RequestParam(value = "customerId", required = true)Long customerId, 
			@RequestParam(value = "collectionId", required = true)Long collectionId,
			@RequestParam(value = "collectionType", required = true)String collectionType,
			@RequestParam(value = "setFollow", required = true)Boolean setfollow) {
		
		return followingService.toggleCollectionFollowedByCustomer(customerId, 
				collectionId, collectionType, setfollow);
	};
	
	@RequestMapping(value = "/api/customer/recomended", method = RequestMethod.GET)
	public Map<String, Object> fetchArtworksRecomendedArtworks(
			@RequestParam(value = "customerId", required = true)Long customerId,
			@RequestParam(value = "limit", required = true) Integer limit,
			@RequestParam(value = "offset", required = true) Integer offset) {
		CriteriaSearchResponse searchResponse = preferenceService
				.getRecomendedArtworksForCustomer(customerId, limit, offset);

		Map<String, Object> wrapResponse = wrapResponse(searchResponse);
		if (customerId != null) { 
			Map<Long, Boolean> favouriteMap = shopifyService.getFavProductsMap(customerId);
			if (favouriteMap.size() > 0) {
				wrapResponse.put("favProductIdMap", favouriteMap);
			}
		} 

		return wrapResponse;
	}
	
	@RequestMapping(value = "/api/customer/favorites", method = RequestMethod.POST)
	public void customerCustomCollection(
			@RequestParam(value = "customerId", required = true)Long customerId, 
			@RequestParam(value = "productId", required = true)Long productId,
			@RequestParam(value = "isLiked", required = true)Boolean isLiked) {
		
		shopifyService.updateFavoritesCollection(customerId, productId, isLiked);
		return;
	};
						
	// Wrap Artwork objects into a Map Helper Function
	private Map<String, Object> wrapResponse(CriteriaSearchResponse searchResponse) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> artworksList = new ArrayList<>();
		if (searchResponse != null) {
			List<ArtWork> artworks = searchResponse.getArtworks();
			for (ArtWork art : artworks) {
				Map<String, Object> artworkMap = new HashMap<String, Object>();
				artworkMap.put("images", art.getImages());
				artworkMap.put("priceBuckets", art.getPriceBuckets());
				artworkMap.put("details", art);
				artworkMap.put("artist", art.getArtist());
				artworksList.add(artworkMap);
			}
			map.put("searchCount", searchResponse.getTotalArtworkCount());
		}
		Object allArtworksCount = artWorkRepository.count();
		map.put("allArtworksCount", allArtworksCount);
		map.put("artworks", artworksList);
		return map;
	}
}

package com.hashedin.artcollective.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.entity.SizeBucket;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.repository.SizeBucketRepository;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.CriteriaSearchResponse;
import com.hashedin.artcollective.service.Frame;
import com.hashedin.artcollective.service.FrameVariantService;
import com.hashedin.artcollective.service.PriceAndSizeBucketService;
import com.hashedin.artcollective.service.Style;
import com.hashedin.artcollective.service.Subject;
import com.hashedin.artcollective.service.TinEyeService;

@RestController
public class ProductsAPI {

	@Autowired
	private ArtWorksService artworkService;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAPI.class);
	
	//Add Price Range Bucket
	@RequestMapping(value = "/manage/priceRange/add", method = RequestMethod.GET)
	public void addPriceBucket(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "lowerRange", required = true) Double lowerRange,
			@RequestParam(value = "upperRange", required = false) Double upperRange) {
		PriceBucket priceBucket = new PriceBucket(id, title, lowerRange, upperRange);
		priceAndSizeBucketService.addPriceBucket(priceBucket);
		LOGGER.info("Price Bucket: " + priceBucket.getTitle() + " Successfully Added");
	
	}
	
	//Add Size Range Bucket
		@RequestMapping(value = "/manage/sizeRange/add", method = RequestMethod.GET)
		public void addSizeBucket(
				@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "title", required = true) String title,
				@RequestParam(value = "lowerValue", required = true) Double lowerRange,
				@RequestParam(value = "upperValue", required = false) Double upperRange) {
			SizeBucket sizeBucket = new SizeBucket(id, title, lowerRange, upperRange);
			priceAndSizeBucketService.addSizeBucket(sizeBucket);
			LOGGER.info("Price Bucket: " + sizeBucket.getTitle() + " Successfully Added");
		
		}
	
	
	@RequestMapping(value = "/manage/priceRange/getall", method = RequestMethod.GET)
	public List<PriceBucket> getAllPriceBuckets() {
		
		return (List<PriceBucket>) priceBucketRepository.findAll();
	}
	
	@RequestMapping(value = "/manage/sizeRange/getall", method = RequestMethod.GET)
	public List<SizeBucket> getAllSizeBuckets() {
		
		return (List<SizeBucket>) sizeBucketRepository.findAll();
	}
	
	// Synchronize data from Shopify into internal Database and Tin Eye
	@RequestMapping(value = "/manage/shopify/synchronize", method = RequestMethod.GET)
	public void synchronizeArtWorks() {
		artworkService.synchronize();
		LOGGER.info("Data Successfully Synchronized");
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
			@RequestParam(value = "offset", required = true) Integer offset) {
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
		return wrapResponse(searchResponse);
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
		List<FrameVariant> frameVariants = frameVariantService.getFrames(frameLength, 
				frameBreadth, mountThickness, frameThickness);
		for (FrameVariant frameVariant : frameVariants) {
			tempFrames.add(new Frame(frameVariant));
		}
		return tempFrames;
	}
	
	@RequestMapping(value = "/api/uploadImage", headers = "content-type=multipart/*", method = RequestMethod.POST)
	@ResponseBody String extractColorsFromImage(@RequestParam("file") MultipartFile file) throws IOException {
		File tempFile = File.createTempFile("uploadedfile", null);
		file.transferTo(tempFile);
		String colors = tinEyeService.extractColorUploadImage(tempFile);
		return colors;
    }
	
	
	

	// Wrap Artwork objects into a Map Helper Function
	private Map<String, Object> wrapResponse(CriteriaSearchResponse searchResponse) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ArtWork> artworks = searchResponse.getArtworks();
		List<Object> artworksList = new ArrayList<>();
		for (ArtWork art : artworks) {
			Map<String, Object> artworkMap = new HashMap<String, Object>();
			artworkMap.put("images", art.getImages());
			artworkMap.put("priceBuckets", art.getPriceBuckets());
			artworkMap.put("details", art);
			artworkMap.put("artist", art.getArtist());
			artworksList.add(artworkMap);
		}
		Object allArtworksCount = artWorkRepository.count();
		map.put("searchCount", searchResponse.getTotalArtworkCount());
		map.put("allArtworksCount", allArtworksCount);
		map.put("artworks", artworksList);
		return map;
	}
}

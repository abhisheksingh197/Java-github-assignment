package com.hashedin.artcollective.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.FrameVariant;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.FrameVariantService;
import com.hashedin.artcollective.service.PriceBucketService;

@RestController
public class ProductsAPI {

	@Autowired
	private ArtWorksService artworkService;
	
	@Autowired
	private ArtWorksSearchService artworksSearchService;
	
	@Autowired
	private FrameVariantService frameVariantService;
	
	@Autowired
	private PriceBucketService priceBucketService;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private PriceBucketRepository priceBucketRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAPI.class);
	
	//Add Price Range Bucket
	@RequestMapping(value = "/manage/priceRange/add", method = RequestMethod.GET)
	public void addPriceBucket(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "lowerRange", required = true) double lowerRange,
			@RequestParam(value = "upperRange", required = true) double upperRange) {
		PriceBucket priceBucket = new PriceBucket(id, title, lowerRange, upperRange);
		priceBucketService.addPriceBucket(priceBucket);
		LOGGER.info("Price Bucket: " + priceBucket.getTitle() + " Successfully Added");
	
	}
	
	
	@RequestMapping(value = "/manage/priceRange/getall", method = RequestMethod.GET)
	public List<PriceBucket> getAllPriceBuckets() {
		
		return (List<PriceBucket>) priceBucketRepository.findAll();
	}
	
	// Synchronize data from Shopify into internal Database and Tin Eye
	@RequestMapping(value = "/manage/shopify/synchronize", method = RequestMethod.GET)
	public void synchronizeArtWorks() {
		artworkService.synchronize();
		LOGGER.info("Data Successfully Synchronized");
	}
	
	
	
	// Search Artworks based on criteria
	@RequestMapping(value = "/api/artworks/search", method = RequestMethod.GET)
	public Map<String, Object> getAllArtworksByCriteria(
			@RequestParam(value = "subjects", required = false) String[] subjects,
			@RequestParam(value = "styles", required = false) String[] styles,
			@RequestParam(value = "colors", required = false) String[] colors,
			@RequestParam(value = "priceBucketRange", required = false) String[] priceBucketRange,
			@RequestParam(value = "medium", required = false) String medium,
			@RequestParam(value = "orientation", required = false) String orientation,
			Pageable page) {
		List<String> subjectList = new ArrayList<>();
		List<String> styleList = new ArrayList<>();
		List<String> colorsList = new ArrayList<>();
		List<String> priceBucketRangeList = new ArrayList<>();
 		subjectList.add("-1");
		styleList.add("-1");
		colorsList.add("");
		priceBucketRangeList.add("-1");
		subjectList = subjects != null ? Arrays.asList(subjects) : subjectList;
		styleList = styles != null ? Arrays.asList(styles) : styleList;
		colors = colors != null ? colors : null;
		medium = (medium !=  null) && (!medium.equalsIgnoreCase("")) ? medium : "-1";
		orientation = (orientation !=  null) && (!orientation.equalsIgnoreCase("")) ? orientation : "-1";
		priceBucketRangeList = priceBucketRange != null ? Arrays.asList(priceBucketRange) 
				: priceBucketRangeList;
		List<ArtWork> artworks = artworksSearchService.findArtworksByCriteria(
				subjectList, 
				styleList,
				colors,
				priceBucketRangeList,
				medium, 
				orientation, 
				page);
		return wrapResponse(artworks);
	}
	
	// Search Tin Eye based on color Criteria
	@RequestMapping(value = "/api/artworks/search/color", method = RequestMethod.GET)
	public Map<String, Object> getAllArtworksByColor(@RequestParam(value = "colors") 
		String[] colors, @RequestParam(value = "weights", required = false) int[] weights) {
		List<ArtWork> artworks = artworksSearchService.findArtworksByColor(colors, weights);
		return wrapResponse(artworks);
	}
	
	// Search For Subjects and Styles
	@RequestMapping(value = "/api/collections", method = RequestMethod.GET)
	public Map<String, Object> getAllSubjects() {
		List<ArtSubject> subjects = (List<ArtSubject>) artSubjectRepository.findAll();
		List<ArtStyle> styles = (List<ArtStyle>) artStyleRepository.findAll();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subjects", subjects);
		map.put("styles", styles);
		return map;
	}
	
	@RequestMapping(value = "/api/frames", method = RequestMethod.GET)
	public List<FrameVariant> getFrames(
			@RequestParam(value = "frameLength", required = true) Double frameLength,
			@RequestParam(value = "frameBreadth", required = true) Double frameBreadth,
			@RequestParam(value = "mountThickness", required = true) Double mountThickness,
			@RequestParam(value = "frameThickness", required = true) Double frameThickness
			) {
		
		return frameVariantService.getFrames(frameLength, frameBreadth, mountThickness, frameThickness);
	}
	
	

	// Wrap Artwork objects into a Map Helper Function
	private static Map<String, Object> wrapResponse(List<ArtWork> artworks) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (ArtWork art : artworks) {
			Map<String, Object> artworkMap = new HashMap<String, Object>();
			artworkMap.put("images", art.getImages());
			artworkMap.put("priceBuckets", art.getPriceBuckets());
			artworkMap.put("details", art);
			artworkMap.put("artist", art.getArtist());
			map.put(art.getTitle(), artworkMap);
		}
		return map;
	}
}

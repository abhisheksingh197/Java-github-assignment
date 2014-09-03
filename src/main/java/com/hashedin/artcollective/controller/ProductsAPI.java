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

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.service.ArtWorksSearchService;
import com.hashedin.artcollective.service.ArtWorksService;
import com.hashedin.artcollective.service.TinEyeService;

@RestController
public class ProductsAPI {

	@Autowired
	private ArtWorksService artworkService;
	
	@Autowired
	private ArtWorksSearchService artworksSearchService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TinEyeService.class);
	
	// Synchronize data from Shopify into internal Database and Tin Eye
	@RequestMapping(value = "/manage/shopify/synchronize", method = RequestMethod.GET)
	public void synchronizeArtWorks() {
		artworkService.synchronize();
		LOGGER.info("Data Successfully Synchronized");
	}
	
	// Search Artworks based on criteria
	@RequestMapping(value = "/api/artworks/search", method = RequestMethod.GET)
	public Map<String, ArtWork> getAllArtworksByCriteria(
			@RequestParam(value = "subjects", required = false) String[] subjects,
			@RequestParam(value = "styles", required = false) String[] styles,
			@RequestParam(value = "collections", required = false) String[] collections,
			@RequestParam(value = "artist", required = false) String artist,
			Pageable page) {
		List<String> subjectList = new ArrayList<>();
		List<String> styleList = new ArrayList<>();
		List<String> collectionList = new ArrayList<>();
		subjectList.add("");
		styleList.add("");
		collectionList.add("");
		subjectList = subjects != null ? Arrays.asList(subjects) : null;
		styleList = styles != null ? Arrays.asList(styles) : null;
		collectionList = collections != null ? Arrays.asList(collections) : null;
		List<ArtWork> artworks = artworksSearchService.findArtworksByCriteria(
				subjectList, 
				styleList,
				collectionList,
				artist,
				page);
		return wrapResponse(artworks);
	}
	
	// Search Tin Eye based on color Criteria
	@RequestMapping(value = "/api/artworks/search/color", method = RequestMethod.GET)
	public Map<String, ArtWork> getAllArtworksByColor(@RequestParam(value = "colors") 
		String[] colors, @RequestParam(value = "weights", required = false) int[] weights) {
		List<ArtWork> artworks = artworksSearchService.findArtworksByColor(colors, weights);
		return wrapResponse(artworks);
	}

	// Wrap Artwork objects into a Map Helper Function
	static Map<String, ArtWork> wrapResponse(List<ArtWork> artworks) {
		Map<String, ArtWork> map = new HashMap<String, ArtWork>();
		for (ArtWork art : artworks) {
			map.put(art.getTitle(), art);
		}
		return map;
	}
}

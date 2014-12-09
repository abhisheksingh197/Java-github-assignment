package com.hashedin.artcollective.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;


@Service
public class PreferenceService {
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private ArtWorkRepository artworkRepository;

	public Map<String, Object> getPreferencesForUser(Long id) {
		Map<String, Object> userPreferences = new HashMap<String, Object>();
		List<MetaField> metafields = shopifyService.getMetaFields("customers", id);
		for (MetaField metafield : metafields) {
			String[] collectionIdsInMetafield = metafield.getValue().split(",");
			for (String collectionId : collectionIdsInMetafield) {
				userPreferences.put(collectionId, collectionId);
			}
		}
		return userPreferences;
	}

	public Map<String, Object> getPreferencesForShop() {
		Map<String, Object> shopPreferences = new HashMap<String, Object>();
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
		shopPreferences.put("subjects", tempSubjects);
		shopPreferences.put("styles", tempStyles);
		shopPreferences.put("mediums", Arrays.asList("canvas", "fine-art"));
		shopPreferences.put("orientations", Arrays.asList("landscape", "potrait", "square"));
		return shopPreferences;
	}
	
	public void updatePreferencesForUser(Long customerId, String[] subjects, 
			String[] styles, String[] mediums, String[] orientations) {
		List<MetaField> metafields = shopifyService.getMetaFields("customers", customerId);
		for (MetaField metafield : metafields) {
			switch (metafield.getKey().split("_")[1]) {
				case "subject" :
					if (subjects.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								subjects, metafield.getId());
					}
					break;
				case "style" :
					if (styles.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								styles, metafield.getId());
					}
					break;
				case "medium" : 
					if (mediums.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								mediums, metafield.getId());
					}
					break;
				case "orientation" :
					if (orientations.length > 0) {
						shopifyService.updateMetafield("customers", String.valueOf(customerId), 
								orientations, metafield.getId());
					}
					break;
				default :
					break;
			}
		}
	}
	
	
	public CriteriaSearchResponse getRecomendedArtworksForCustomer(Long customerId, 
			int limit, int offset) {
		List<MetaField> metafields = shopifyService.getMetaFields("customers", customerId);
		List<String> subjects = new ArrayList<>();
		List<String> styles = new ArrayList<>();
		String medium = "";
		String orientation = "";
 		for (MetaField metafield : metafields) {
			switch (metafield.getKey().split("_")[1]) {
				case "subject" :
					subjects = Arrays.asList(metafield.getValue().split(","));
					break;
				case "style" :
					styles = Arrays.asList(metafield.getValue().split(","));
					break;
				case "medium" : 
					medium = metafield.getValue();
					break;
				case "orientation" :
					orientation = metafield.getValue();
					break;
				default :
					break;
			}
		}
 		List<ArtWork> artworks = artworkRepository.findByPreference(
 				styles, subjects, medium, orientation, limit, offset);
 		int totalArtworkCount = artworkRepository.findCountByPreference(styles, 
 				subjects, medium, orientation);
 		CriteriaSearchResponse searchResponse = new CriteriaSearchResponse(artworks, 
 				totalArtworkCount);
 		return searchResponse;
	}
	
	

}

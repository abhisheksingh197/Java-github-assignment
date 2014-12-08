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
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;


@Service
public class PreferenceService {
	
	@Autowired
	private ShopifyService shopifyService;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;

	public Map<String, Object> getPreferencesForUser(Long id) {
		Map<String, Object> userPreferences = new HashMap<String, Object>();
		List<MetaField> metafields = shopifyService.getMetaFields("customers", id);
		for (MetaField metafield : metafields) {
			String[] collectionIdsInMetafield = metafield.getValue().split(",");
			for (String collectionId : collectionIdsInMetafield) {
				userPreferences.put(collectionId, collectionId);
			}
		}
		userPreferences.put("123", "345");
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
		shopPreferences.put("orientations", Arrays.asList("Landscape", "Portrait", "Square"));
		return shopPreferences;
	}

}

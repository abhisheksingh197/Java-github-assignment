package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtCollection;
import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;

@Service
public class ArtWorksSearchService {
	
	@Autowired
	private ArtWorkRepository artWorkRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private ArtCollectionsRepository artCollectionsRepository;
	
	@Autowired
	private TinEyeService tinEyeService;
		
	public List<ArtWork> findArtworksByArtist(String firstName) {
		List<ArtWork> artWorkList = artWorkRepository.findByArtist(firstName);
		return artWorkList;
	}
	
	public List<ArtWork> findArtworksByCriteria(
			List<String> subjectList, 
			List<String> styleList,
			List<String> collectionList,
			String artist,
			String priceBucketRange,
			Pageable page) {
		List<ArtSubject> artSubjectList = (List<ArtSubject>) artSubjectRepository.findAll();
		List<String> defaultSubjectList = new ArrayList<>();
		for (ArtSubject artSubject : artSubjectList) {
			defaultSubjectList.add(artSubject.getTitle());
		}
		if (subjectList == null) {
			subjectList = defaultSubjectList;
		}
		List<ArtStyle> artStyleList = (List<ArtStyle>) artStyleRepository.findAll();
		List<String> defaultStyleList = new ArrayList<>();
		for (ArtStyle artStyle : artStyleList) {
			defaultStyleList.add(artStyle.getTitle());
		}
		if (styleList == null) {
			styleList = defaultStyleList;
		}
		List<ArtCollection> artCollectionsList = (List<ArtCollection>) artCollectionsRepository.findAll();
		List<String> defaultCollectionsList = new ArrayList<>();
		for (ArtCollection artCollection : artCollectionsList) {
			defaultCollectionsList.add(artCollection.getTitle());
		}
		if (collectionList == null) {
			collectionList = defaultCollectionsList;
		}
		List<ArtWork> artWorkList = artWorkRepository.findByCriteria(
				subjectList,
				styleList,
				collectionList,
				artist,
				priceBucketRange,
				page);
		return artWorkList;
	}
	
	public List<ArtWork> findArtworksByColor(String[] colors, int[] weights) {
		ArtSearchCriteria searchCriteria = new ArtSearchCriteria();
		if (colors.length > 1) {
			searchCriteria.setColour1(colors[0]);
			searchCriteria.setColour2(colors[1]);
		}
		if (colors.length == 1) {
			searchCriteria.setColour1(colors[0]);
		}
		if (weights != null && weights.length > 1) {
			searchCriteria.setColor1Weight(weights[0]);
			searchCriteria.setColor2Weight(weights[1]);
		}
		if (weights != null && weights.length == 1) {
			searchCriteria.setColor1Weight(weights[0]);
		}
		List<ArtWork> artWorks = tinEyeService.getMatchingArtWorks(searchCriteria);
		return artWorks;
	}

}

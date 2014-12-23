package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.repository.ArtCollectionsRepository;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.ArtistRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;

@Service
public class ArtWorksSearchService {
	
	//CHECKSTYLE:OFF
	@Autowired
	public ArtWorkRepository artWorkRepository;
	//CHECKSTYLE:ON
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	private ArtStyleRepository artStyleRepository;
	
	@Autowired
	private ArtCollectionsRepository artCollectionsRepository;
	
	@Autowired
	private PriceBucketRepository priceBucketRespository;
	
	@Autowired
	private TinEyeService tinEyeService;
	
	@Cacheable(value = "artworksearch")
	//CHECKSTYLE:OFF
	public CriteriaSearchResponse findArtworksByCriteria(
			List<String> subjectList, 
			List<String> styleList,
			String[] colorsList,
			List<String> priceBucketRangeList,
			String medium, 
			String orientation,
			List<String> sizeBucketRangeList,
			Integer limit,
			Integer offset){
	//CHECKSTYLE:ON
			
		
		boolean isColorSearch = false;
		List<Long> idList = getIdListPostColorSearch(colorsList);
		if (idList.get(0) != -1) {
			isColorSearch = true;
		}
		//LOGGER.info(idList.toString());
		List<ArtWork> artWorkList = artWorkRepository.findByCriteria(styleList,
				subjectList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				idList,
				limit,
				offset);
		
		int artworkCount = artWorkRepository.findCountByCriteria(styleList,
				subjectList,
				priceBucketRangeList,
				medium, 
				orientation,
				sizeBucketRangeList,
				idList);
		
		/* If the search includes a color list, we sort the artworks resulted from 
		   the query by the order in which color search results. */
		if (isColorSearch) {
			ArtWorkComparatorByColourMatch comparator = new ArtWorkComparatorByColourMatch(idList);
			Collections.sort(artWorkList, comparator);
		}
		CriteriaSearchResponse searchResponse = new CriteriaSearchResponse(artWorkList, artworkCount);
		return searchResponse;
	}
	
	private List<Long> getIdListPostColorSearch(String[] colorsList) {
		int[] weights = null;

		List<Long> nullIdList = new ArrayList<>();
		nullIdList.add(-1L);
		// If color list in null, we are avoiding tinEye API Call.
		if (colorsList != null) {
			CriteriaSearchResponse searchResponse = findArtworksByColor(colorsList, weights);
			List<ArtWork> artworks = searchResponse.getArtworks();
			// adding a list of Ids which consist -1 so that SQL Query parses response 
				// with no artwork from TinEye
			return (artworks.size() == 0 ? nullIdList : getArtworkIds(artworks));
		}
		else {
			return nullIdList;
		}
	}

	public List<Long> getArtworkIds(List<ArtWork> artworks) {
		
		List<Long> idList = new ArrayList<>();
		for (ArtWork art : artworks) {
			idList.add(art.getId());
		}
		return idList;
	}
	
	public CriteriaSearchResponse findArtworksByColor(String[] colors, int[] weights) {
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
			CriteriaSearchResponse searchResponse = new CriteriaSearchResponse(artWorks, artWorks.size());
			return searchResponse;
	}
}

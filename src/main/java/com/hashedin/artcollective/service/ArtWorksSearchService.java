package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		
	public List<ArtWork> findArtworksByArtist(String firstName) {
		List<ArtWork> artWorkList = artWorkRepository.findByArtist(firstName);
		return artWorkList;
	}
	
	//CHECKSTYLE:OFF
	public CriteriaSearchResponse findArtworksByCriteria(
			List<String> subjectList, 
			List<String> styleList,
			String[] colorsList,
			List<String> priceBucketRangeList,
			String medium, 
			String orientation,
			Integer limit,
			Integer offset){
	//CHECKSTYLE:ON
			
		
		
		List<Long> idList = getIdListPostColorSearch(colorsList);
		
		//LOGGER.info(idList.toString());
		List<ArtWork> artWorkList = artWorkRepository.findByCriteria(styleList,
				subjectList,
				priceBucketRangeList,
				medium, 
				orientation,
				idList,
				limit,
				offset);
		int artworkCount = artWorkRepository.findCountByCriteria(styleList,
				subjectList,
				priceBucketRangeList,
				medium, 
				orientation,
				idList);
		
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
		HashSet<Long> idHashSet = new HashSet<>();
		List<Long> idList = new ArrayList<>();
		for (ArtWork art : artworks) {

			idHashSet.add(art.getId());
			
		}
		idList.addAll(idHashSet);
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

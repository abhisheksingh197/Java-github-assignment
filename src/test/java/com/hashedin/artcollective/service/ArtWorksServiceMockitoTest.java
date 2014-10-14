package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hashedin.artcollective.BaseUnitTest;
import com.hashedin.artcollective.entity.ArtStyle;
import com.hashedin.artcollective.entity.ArtSubject;
import com.hashedin.artcollective.entity.ArtWork;
import com.hashedin.artcollective.entity.PriceBucket;
import com.hashedin.artcollective.repository.ArtStyleRepository;
import com.hashedin.artcollective.repository.ArtSubjectRepository;
import com.hashedin.artcollective.repository.ArtWorkRepository;
import com.hashedin.artcollective.repository.PriceBucketRepository;

import static org.junit.Assert.*;
public class ArtWorksServiceMockitoTest extends BaseUnitTest {
	
	@Autowired
	ArtWorkRepository artWorkRepository;
	
	@Autowired
	ArtSubjectRepository artSubjectRepository;
	
	@Autowired
	ArtStyleRepository artStyleRepository;
	
	@Autowired
	PriceBucketRepository priceBucketRespository;
	
	@Autowired
	ArtWorkRepository artworkrepository;
	
	@Autowired
	PriceBucketRepository priceBuckettRepository;
	
	@Autowired
	ArtWorksSearchService artworksSearchService;
	
	@Test
	public void testArtwork() {
		List<ArtWork> artworks = generateThousandArtWorks();
		artworkrepository.save(artworks);
		ArtWorksSearchService testService = new ArtWorksSearchService() {

			@Override
			public CriteriaSearchResponse findArtworksByColor(String[] colors,
					int[] weights) {
				return generateReturnColorByArtworks();
			}};
			testService.artWorkRepository = this.artworkrepository;
			
			int offset = 0;
			int limit = 151;
			List<String> subjects = new ArrayList<>();
			subjects.add("123");
			List<String> styles = new ArrayList<>();
			styles.add("-1");
			List<String> priceBucket = new ArrayList<>();
			priceBucket.add("-1");
			String medium = null;
			String orientation = null;
		CriteriaSearchResponse searchResponse = testService.findArtworksByCriteria(subjects, styles, new String[] {"fffff"}, priceBucket, 
				medium, orientation, limit, offset);
		
		assertEquals(searchResponse.getTotalArtworkCount(),151);

		
	}
	
	private List<ArtWork> generateThousandArtWorks() {
		ArtSubject subject = new ArtSubject(123L, "goodSubject");
		ArtStyle style = new ArtStyle(234L, "goodStyle");
		PriceBucket priceBucket = new PriceBucket();
		priceBucket.setId(897L);
		priceBucket.setLowerRange(2500);
		priceBucket.setTitle("low");
		priceBucket.setUpperRange(5000);

		
		artSubjectRepository.save(subject);
		artStyleRepository.save(style);
		priceBuckettRepository.save(priceBucket);
		
		List<ArtSubject> artSubjects = new ArrayList<>();
		List<ArtStyle> artStyles = new ArrayList<>();
		List<PriceBucket>priceBuckets = new ArrayList<>();
		
		artSubjects.add(subject);
		artStyles.add(style);
		priceBuckets.add(priceBucket);
		
		List<ArtWork> artworks = new ArrayList<>();
		long idCount = 1;
		for (int artworkIterator = 0; artworkIterator < 1000; artworkIterator++ ) {
			ArtWork art = new ArtWork();
			art.setId(idCount);
			art.setTitle("Artwork-" + idCount);
			art.setSubject(artSubjects);
			art.setStyle(artStyles);
			art.setPriceBuckets(priceBuckets);
			art.setMedium("canvas");
			art.setOrientation("landscape");
			artworks.add(art);
			idCount++;
		}
		
		return artworks;
	}
	
	private CriteriaSearchResponse generateReturnColorByArtworks() {
		List<ArtWork> artworks = new ArrayList<>();
		long idCount = 150;
		for (int artworkIterator = 149; artworkIterator < 300; artworkIterator++ ) {
			ArtWork art = new ArtWork();
			art.setId(idCount);
			art.setTitle("Artwork-" + idCount);
			artworks.add(art);
			idCount ++;
		}
		artworks.removeAll(Collections.singleton(null));
		CriteriaSearchResponse searchResponse = new CriteriaSearchResponse(artworks, artworks.size());
		return searchResponse;
	}
	
}

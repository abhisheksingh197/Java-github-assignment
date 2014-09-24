package com.hashedin.artcollective.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
	
	@Ignore
	@Test
	public void testArtwork() {
		List<ArtWork> artworks = generateThousandArtWorks();
		artworkrepository.save(artworks);
		ArtWorksSearchService testService = new ArtWorksSearchService() {

			@Override
			public List<ArtWork> findArtworksByColor(String[] colors,
					int[] weights) {
				return generateReturnColorByArtworks();
			}};
			testService.artWorkRepository = this.artworkrepository;
			
			int pageNo = 0;
			Pageable page = new PageRequest(pageNo, 200);
			List<String> subjects = new ArrayList<>();
			subjects.add("goodSubject");
		List<ArtWork> arts = testService.findArtworksByCriteria(subjects, null, new String[] {"fffff"}, null, null, null, page);
		
		assertEquals(arts.size(),151);

		
	}
	
	private List<ArtWork> generateThousandArtWorks() {
		ArtSubject subject = new ArtSubject();
		subject.setId(123L);
		subject.setTitle("goodSubject");
		ArtStyle style = new ArtStyle();
		style.setId(234L);
		style.setTitle("goodStyle");
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
	
	private List<ArtWork> generateReturnColorByArtworks() {
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
		return artworks;
	}
	
}
